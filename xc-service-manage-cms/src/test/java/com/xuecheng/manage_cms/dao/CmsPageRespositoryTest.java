package com.xuecheng.manage_cms.dao;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRespositoryTest {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private GridFSBucket gridFSBucket;

    @Test
    public void test1(){

        ResponseEntity<Map> forEntity = restTemplate.getForEntity("http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f", Map.class);
        Map map = forEntity.getBody();
        System.out.println(map.toString());
    }

    /**
     * 存文件
     * @throws FileNotFoundException
     */
    @Test
    public void testGridFs() throws FileNotFoundException {

        File file = new File("d:/index_banner.ftl");
        FileInputStream fileInputStream = new FileInputStream(file);
        GridFSFile store = gridFsTemplate.store(fileInputStream, "index_banner.ftl");
        System.out.println(store.toString());
    }

    /**
     * 取文件
     */
    @Test
    public void queryFile() throws IOException {

        String filedId = "5ce6560d3309c13f74d91241";
        //根据ID查询文件
        GridFSDBFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(filedId)));
        //打开下载流对象
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getFilename());
        //创建gridFsResource，用于获取流对象
        GridFsResource gridFsResource = new GridFsResource(gridFSFile);
        //获取流中的数据
        String s = IOUtils.toString(gridFsResource.getInputStream());
        System.out.println(s);
    }
}
