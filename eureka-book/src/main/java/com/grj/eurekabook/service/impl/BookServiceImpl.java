package com.grj.eurekabook.service.impl;

import com.grj.eurekabook.entity.Book;
import com.grj.eurekabook.mapper.BookMapper;
import com.grj.eurekabook.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author guorenjie
 * @since 2019-09-27
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

}
