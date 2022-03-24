package com.qin.web.book_borrow.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qin.exception.BusinessException;
import com.qin.exception_advice.BusinessExceptionEnum;
import com.qin.web.book_borrow.entity.*;
import com.qin.web.book_borrow.mapper.BorrowBookMapper;
import com.qin.web.book_borrow.service.BorrowBookService;
import com.qin.web.sys_books.entity.SysBooks;
import com.qin.web.sys_books.service.SysBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * @author 秦家乐
 * @date 2022/3/19 17:48
 */
@Service
public class BorrowBookServiceImpl extends ServiceImpl<BorrowBookMapper, BorrowBook> implements BorrowBookService {
    
    @Autowired
    private SysBooksService sysBooksService;
    
    //加锁，防止只剩一本的情况下两个人同时查询和借阅了
    private Lock lock = new ReentrantLock();
    
    @Override
    @Transactional
    //涉及到多个表或者对数据的更新，记得添加事务
    public void borrow(BorrowParm parm,String userType) {
        //加锁
        lock.lock();
        try {
            
            //构造查询条件，先查出符合的书
            QueryWrapper<SysBooks> query = new QueryWrapper();
            query.lambda().in(SysBooks::getBookId, parm.getBookIds());
            List<SysBooks> list = sysBooksService.list(query);
            
            //查看库存是否充足
            //filter:遍历并筛选出满足条件的元素形成一个新的 Stream 流。
            //longValue:用来得到Long类中的数值
            //Collectors.toList()转换成list，然后关闭流
            //这一行代码的作用是查询出里面库存小于1的
            List<SysBooks> collect = list.stream().filter(item -> item.getBookStore().longValue() < 1L).collect(Collectors.toList());
            //大于0表示查询到数据了
            if (collect.size() > 0) {
                //提示那本书库存不足
                //通过map中的getBookName，作为k来获取v
                List<String> stringList = collect.stream().map(SysBooks::getBookName).collect(Collectors.toList());
                //自定义的异常，code和message，然后就抛出去不向后走了
                throw new BusinessException(BusinessExceptionEnum.NO_STOCK.getCode(),
                        stringList + BusinessExceptionEnum.NO_STOCK.getMessage());
            }
            
            //减少库存，插入借书明细
            List<Long> bookIds = parm.getBookIds();
            //试试forEach和for
            for (int i = 0; i < bookIds.size(); i++) {
                Long bookId = bookIds.get(i);
                //减库存
                int res = sysBooksService.subBook(bookId);
                //如果>0才表示减少成功
                if (res > 0) {
                    BorrowBook borrowBook = new BorrowBook();
                    borrowBook.setBookId(bookId);
                    borrowBook.setReaderId(parm.getReaderId());
                    borrowBook.setReturnTime(parm.getReturnTime());
                    
                    //userType等于0的时候是读者，1是管理
                    //borrowBook设置为0为审核中
                    //如果借书的是管理，那么审核直接通过
                    //如果借书的是读者，则需要审核
                    if(userType.equals("0")){
                        borrowBook.setApplyStatus("0");
                        borrowBook.setBorrowStatus("0");
                    }else if (userType.equals("1")){
                        borrowBook.setApplyStatus("1");
                        borrowBook.setBorrowStatus("1");
                    }else {
                        throw new BusinessException(500,"用户类型不存在，无法借书");
                    }
                    
                    
                    borrowBook.setBorrowTime(new Date());
                    //插入明细
                    this.baseMapper.insert(borrowBook);
                }
                
            }
            
            
        } finally {
            //释放锁
            lock.unlock();
            
        }
        
        
    }
    
    @Override
    public IPage<ReturnBook> getBorrowList(ListParm parm) {
        //构造分页对象
        Page<ReturnBook> page = new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        
        return this.baseMapper.getBorrowList(page, parm);
    }
    
    @Override
    @Transactional
    public void returnBook(List<ReturnParm> list) {
        //加库存，变更借书状态
        for (int i = 0; i < list.size(); i++) {
            //加库存
            int res = sysBooksService.addBook(list.get(i).getBookId());
            if(res>0){
                //变更借书状态
                BorrowBook borrowBook=new BorrowBook();
                borrowBook.setBorrowId(list.get(i).getBorrowId());
                borrowBook.setBorrowStatus("2");//2代表已还
                borrowBook.setReturnStatus("1");//正常还书
                this.baseMapper.updateById(borrowBook);
                
            }
        }
        
        
    }
    
    @Override
    public void exceptionBook(ExceptionParm parm) {
        // 0: 异常、破损  1：丢失 ：不能还库存
        String type = parm.getType();
        if(type.equals("0")){
            System.out.println("-----------"+parm.getExceptionText());
            //加库存
            int res = sysBooksService.addBook(parm.getBookId());
            if(res > 0){
                //变更借书状态
                BorrowBook borrowBook = new BorrowBook();
                borrowBook.setBorrowId(parm.getBorrowId());
                borrowBook.setBorrowStatus("2"); //已还
                borrowBook.setReturnStatus("2"); //异常还书
                borrowBook.setExcepionText(parm.getExceptionText());
                this.baseMapper.updateById(borrowBook);
            }
        }else{ //丢失
            //变更借书状态
            BorrowBook borrowBook = new BorrowBook();
            borrowBook.setBorrowId(parm.getBorrowId());
            borrowBook.setBorrowStatus("2"); //已还
            borrowBook.setReturnStatus("3"); //丢失
            borrowBook.setExcepionText(parm.getExceptionText());
            this.baseMapper.updateById(borrowBook);
        }
    
    }
    
    @Override
    public IPage<LookBorrow> getLookBorrowList(LookParm parm) {
        //构造分页对象
        System.out.println("----------------------------------");
        Page<LookBorrow> page =new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        System.out.println("--------------------------------");
        return this.baseMapper.getLookBorrowList(page,parm);
    }
    
    @Override
    public IPage<LookBorrow> getReaderLookBorrowList(LookParm parm) {
        //构造分页对象
        Page<LookBorrow> page =new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        
        return this.baseMapper.getReaderLookBorrowList(page,parm);
    }
    
}
