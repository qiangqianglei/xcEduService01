package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRespositoryTest {

    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Test
    public void testFindAll(){

        List<CmsPage> all = cmsPageRepository.findAll();
        System.out.println(all);
    }

    @Test
    public void testFindPage(){

        int page = 2;
        int size = 10;
        Pageable pageable = new PageRequest(page,size);
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        System.out.println(all);
    }

    @Test
    public void testAdd(){
        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageName("测试页面");
        cmsPage.setPageAliase("test page");
        CmsPage save = cmsPageRepository.save(cmsPage);
        System.out.println(save);
    }

    @Test
    public void updateTest(){

        //先查询
        CmsPage one = cmsPageRepository.findOne("5cc7ebec409a9f0cac7f8a1c");
        //在修改
        one.setPageName("修改后的测试页面");
        CmsPage save = cmsPageRepository.save(one);
        System.out.println(save);
    }

    /**
     * 测试自定义的查询
     */
    @Test
    public void TestFindByPageName(){

        CmsPage cmsPage = cmsPageRepository.findByPageName("修改后的测试页面");

        System.out.println(cmsPage);
    }


    @Test
    public void testCountByPageAliase(){

        int i = cmsPageRepository.countByPageAliase("课程详情页面");
        System.out.println(i);
    }

    /**
     * 测试删除
     */
    @Test
    public void TestDelete(){
        cmsPageRepository.delete("5cc7ebec409a9f0cac7f8a1c");
    }
}
