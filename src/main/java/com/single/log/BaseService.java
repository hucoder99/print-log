package com.single.log;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.single.log.module.PageQuery;

import java.util.List;
import java.util.function.Consumer;

/**
 * 服务基类
 *
 * @author single
 * @date 2022/6/10
 */
public interface BaseService<T, DTO> extends IService<T> {

    /**
     * 添加或者修改DO
     *
     * @param dto DTO
     * @return 主键
     */
    Integer addOrUpdate(DTO dto);

    /**
     * 根据id删除DO
     *
     * @param id 主键
     * @return 成功或者失败
     */
    Boolean logicRemoveById(Integer id);

    /**
     * 根据ID查询详情
     *
     * @param id 主键
     * @return DTO
     */
    DTO findById(Integer id);

    /**
     * 查询所有
     *
     * @param queryWrapper Wrapper
     * @return List DTO
     */
    List<DTO> listAll(Wrapper<T> queryWrapper);

    /**
     * 查询所有
     *
     * @return List DTO
     */
    List<DTO> listAll();

    /**
     * 分页查询
     *
     * @param pageQuery 分页参数
     * @return 分页结果
     */
    PageDTO<DTO> page(PageQuery pageQuery);

    /**
     * 分页查询
     *
     * @param pageQuery    分页参数
     * @param queryWrapper Wrapper
     * @return 分页结果
     */
    PageDTO<DTO> page(PageQuery pageQuery, Wrapper<T> queryWrapper);

    /**
     * 分页查询
     *
     * @param pageQuery    分页参数
     * @param queryWrapper Wrapper
     * @param consumer     对分页结果进行一些属性转换操作
     * @return 分页结果
     */
    PageDTO<DTO> page(PageQuery pageQuery, Wrapper<T> queryWrapper, Consumer<List<DTO>> consumer);

}
