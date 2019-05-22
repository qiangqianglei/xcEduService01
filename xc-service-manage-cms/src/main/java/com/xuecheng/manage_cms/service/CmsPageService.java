package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.CustomerException;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.apache.commons.lang3.StringUtils;
import org.junit.validator.PublicClassValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class CmsPageService {

    @Autowired
    private CmsPageRepository cmsPageRepository;

    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {

        CmsPage cmsPage = new CmsPage();
        //条件匹配器,页面别名模糊匹配
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        //站点ID
        if (StringUtils.isNotEmpty(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        //模板ID
        if (StringUtils.isNotEmpty(queryPageRequest.getTemplateId())) {
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        }
        //页面别名
        if (StringUtils.isNotEmpty(queryPageRequest.getPageAliase())) {
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }

        page = page - 1; //为了适应MongoDB的接口将页码-1
        if (size < 0) {
            size = 10;
        }
        Pageable pageable = new PageRequest(page, size);
        //创建条件实例
        Example<CmsPage> example = Example.of(cmsPage, matcher);
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);

        QueryResult<CmsPage> queryResult = new QueryResult<CmsPage>();
        queryResult.setList(all.getContent());
        queryResult.setTotal(all.getTotalElements());

        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, queryResult);

        return queryResponseResult;
    }

    /**
     * 新增页面
     */
    public CmsPageResult add(CmsPage cmsPage) {

        //先校验页面是否存在
        CmsPage cmsPage1 = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());

        if(cmsPage1 != null){
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }

        CmsPage save = cmsPageRepository.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS, save);
    }

    /**
     * 根据id查询
     */
    public CmsPage getById(String id) {

        CmsPage cmsPage =  cmsPageRepository.findOne(id);
        return cmsPage;
    }

    /**
     * 编辑页面
     */
    public CmsPageResult update(String id, CmsPage cmsPage) {

        //根据id查询页面
        CmsPage byId = cmsPageRepository.findOne(id);
        if (byId != null) {
            //更新模板id

            byId.setTemplateId(cmsPage.getTemplateId());
            //更新所属站点
            byId.setSiteId(cmsPage.getSiteId());
            //更新页面别名
            byId.setPageAliase(cmsPage.getPageAliase());
            //更新页面名称
            byId.setPageName(cmsPage.getPageName());
            //更新访问路径
            byId.setPageWebPath(cmsPage.getPageWebPath());
            //更新物理路径
            byId.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            CmsPage save = cmsPageRepository.save(byId);
            if(save != null){
                return new CmsPageResult(CommonCode.SUCCESS,save);
            }

        }
        return new CmsPageResult(CommonCode.FAIL,null);
    }

    /**
     * 删除页面
     */
    public ResponseResult delete(String id){

        CmsPage one = cmsPageRepository.findOne(id);
        if(one != null) {
            cmsPageRepository.delete(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }
}
