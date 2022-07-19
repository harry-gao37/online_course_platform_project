package com.course.cms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.course.vo.crm.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author YIFU
 * @since 2022-07-05
 */
public interface CrmBannerService extends IService<CrmBanner> {

    CrmBanner getBannerById(String id);

    void saveBanner(CrmBanner banner);

    void updateBanner(CrmBanner banner);

    void removeBannerById(String id);

    void pageBanner(Page<CrmBanner> pageParam, Object o);

    List<CrmBanner> selectIndexList();
}
