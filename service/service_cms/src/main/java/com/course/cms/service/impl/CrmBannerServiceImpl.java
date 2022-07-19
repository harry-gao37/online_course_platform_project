package com.course.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.course.vo.crm.CrmBanner;
import com.course.cms.mapper.CrmBannerMapper;
import com.course.cms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author YIFU
 * @since 2022-07-05
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    public CrmBanner getBannerById(String id) {
        CrmBanner crmBanner = baseMapper.selectById(id);
        return crmBanner;
    }

    @CacheEvict(value = "banner", allEntries=true)
    @Override
    public void saveBanner(CrmBanner banner) {
        baseMapper.insert(banner);
    }

    @CacheEvict(value = "banner", allEntries=true)
    @Override
    public void updateBanner(CrmBanner banner) {
        baseMapper.updateById(banner);
    }

    @CacheEvict(value = "banner", allEntries=true)
    @Override
    public void removeBannerById(String id) {
        baseMapper.deleteById(id);

    }

    @Override
    public void pageBanner(Page<CrmBanner> pageParam, Object o) {
        baseMapper.selectPage(pageParam, null);

    }

    @Cacheable(value = "banner", key = "'selectIndexList'")
    @Override
    public List<CrmBanner> selectIndexList() {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 2");
        List<CrmBanner> crmBanners = baseMapper.selectList(wrapper);
        return crmBanners;
    }
}
