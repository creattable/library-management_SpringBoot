package com.qin.web.book_borrow.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qin.exception.BusinessException;
import com.qin.exception_advice.BusinessExceptionEnum;
import com.qin.web.book_borrow.entity.BorrowBook;
import com.qin.web.book_borrow.entity.BorrowParm;
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
    public void borrow(BorrowParm parm) {
        //加锁
        lock.lock();
        try {
            
            //构造查询条件，先查出符合的书
            QueryWrapper<SysBooks> query =new QueryWrapper();
            query.lambda().in(SysBooks::getBookId, parm.getBookIds());
            List<SysBooks> list = sysBooksService.list(query);
            
            //查看库存是否充足
            //filter:遍历并筛选出满足条件的元素形成一个新的 Stream 流。
            //longValue:用来得到Long类中的数值
            //Collectors.toList()转换成list，然后关闭流
            //这一行代码的作用是查询出里面库存小于1的
            List<SysBooks> collect = list.stream().filter(item -> item.getBookStore().longValue() < 1L).collect(Collectors.toList());
            //大于0表示查询到数据了
            if(collect.size()>0){
                //提示那本书库存不足
                //通过map中的getBookName，作为k来获取v
                List<String> stringList = collect.stream().map(SysBooks::getBookName).collect(Collectors.toList());
                //自定义的异常，code和message，然后就抛出去不向后走了
                throw new BusinessException(BusinessExceptionEnum.NO_STOCK.getCode(),
                        stringList+ BusinessExceptionEnum.NO_STOCK.getMessage());
            }
    
            //减少库存，插入借书明细
            List<Long> bookIds = parm.getBookIds();
            //试试forEach和for
            for(int i=0;i<bookIds.size();i++){
                Long bookId=bookIds.get(i);
                //减库存
                int res= sysBooksService.subBook(bookId);
                //如果>0才表示减少成功
                if(res>0){
                    BorrowBook borrowBook = new BorrowBook();
                    borrowBook.setBookId(bookId);
                    borrowBook.setReaderId(parm.getReaderId());
                    borrowBook.setReturnTime(parm.getReturnTime());
                    borrowBook.setApplyStatus("1");
                    borrowBook.setBorrowStatus("1");
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
    
}
