package com.grj.eurekabook.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.grj.eurekabook.client.UserClient;
import com.grj.eurekabook.entity.Book;
import com.grj.eurekabook.mapper.BookMapper;
import com.grj.eurekabook.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author guorenjie
 * @since 2019-09-22
 */
@Api(tags = "书籍管理")
@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserClient userClient;

    @ApiOperation("获取所有书籍")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页",dataType = "long",defaultValue = "1", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数",dataType = "long", defaultValue = "5", required = true)
    })
    @RequestMapping("/getAll")
    public IPage<Book> getAll(long currentPage, long pageSize){
        return bookService.page(new Page<Book>(currentPage,pageSize));
    }
    @ApiOperation("添加书籍")
    @ApiImplicitParam(name = "book", value = "book对象",dataType = "Book",defaultValue = "book", required = true)
    @RequestMapping("/addBook")
    public boolean addBook(Book book){
        return bookService.save(book);
    }

    /**
     * 服务调用feign
     * 测试ribbon以及hystrix
     * @return
     */
    @RequestMapping("/getAllUserList")
    public List<Map<String,Object>> getAllUserList(){
        return userClient.getAllUserList();
    }
}
