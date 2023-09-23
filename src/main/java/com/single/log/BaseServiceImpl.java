package com.single.log;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.TypeUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.single.log.module.BaseDO;
import com.single.log.module.PageQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author single
 * @date 2022/6/10
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseDO, DTO> extends ServiceImpl<M, T> implements BaseService<T, DTO> {

	/*** id*/
	public static final String ID_COLUMN = "id";
	/*** 状态*/
	public static final String STATUS_COLUMN = "status";

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Integer addOrUpdate(DTO dto) {
		T dataObject = BeanUtil.copyProperties(dto, getDoClass());
		saveOrUpdate(dataObject);

		return dataObject.getId();
	}

	@Override
	public Boolean logicRemoveById(Integer id) {
		UpdateWrapper<T> updateWrapper = new UpdateWrapper<>();
//		updateWrapper.set(STATUS_COLUMN, DeletedEnum.DELETED.getValue());
		updateWrapper.eq(ID_COLUMN, id);
		return update(null, updateWrapper);
	}

	@Override
	public DTO findById(Integer id) {
		return Optional.ofNullable(getBaseMapper().selectById(id)).map(item -> BeanUtil.copyProperties(item, getDtoClass())).orElse(null);
	}

	@Override
	public List<DTO> listAll(Wrapper<T> queryWrapper) {
		return BeanUtil.copyToList(list(queryWrapper), getDtoClass());
	}

	@Override
	public List<DTO> listAll() {
		return BeanUtil.copyToList(list(defaultQueryWrapper(true)), getDtoClass());
	}

	/**
	 * 默认获取未删除的按照id倒序
	 *
	 * @param isLogicRemove 是否逻辑删除
	 * @return QueryWrapper
	 */
	protected QueryWrapper<T> defaultQueryWrapper(boolean isLogicRemove) {
		QueryWrapper<T> queryWrapper = new QueryWrapper<>();
//		queryWrapper.in(isLogicRemove, STATUS_COLUMN, DeletedEnum.NOT_DELETE).orderByDesc(ID_COLUMN);
		return queryWrapper;
	}


	@Override
	public PageDTO<DTO> page(PageQuery pageQuery) {
		return page(pageQuery, defaultQueryWrapper(true));
	}

	@Override
	public PageDTO<DTO> page(PageQuery pageQuery, Wrapper<T> queryWrapper) {
		return page(pageQuery, queryWrapper, null);
	}

	@Override
	public PageDTO<DTO> page(PageQuery pageQuery, Wrapper<T> queryWrapper, Consumer<List<DTO>> consumer) {
		Page<T> page = page(Page.of(pageQuery.getPageNo(), pageQuery.getPageSize()), queryWrapper);
		page.setOrders(pageQuery.getOrders());
		// DO LIST-> DTO LIST
		List<DTO> dtoList = BeanUtil.copyToList(page.getRecords(), getDtoClass());
		page.setRecords(Collections.emptyList());

		PageDTO<DTO> pageDTO = new PageDTO<>();
		BeanUtil.copyProperties(page, pageDTO);

		pageDTO.setRecords(dtoList);

		if (Objects.nonNull(consumer) && CollUtil.isNotEmpty(dtoList)) {
			consumer.accept(dtoList);
		}
		return pageDTO;
	}

	/**
	 * 获取DO的类型
	 *
	 * @return DO Class
	 */
	@SuppressWarnings("unchecked")
	protected Class<T> getDoClass() {
		return (Class<T>) TypeUtil.getTypeArgument(this.getClass().getGenericSuperclass(), 1);
	}

	/**
	 * 获取DTO的类型
	 *
	 * @return DTO Class
	 */
	@SuppressWarnings("unchecked")
	protected Class<DTO> getDtoClass() {
		return (Class<DTO>) TypeUtil.getTypeArgument(this.getClass().getGenericSuperclass(), 2);
	}

	/**
	 * 是否存在指定的列名称，表中没有状态名称
	 *
	 * @param fieldName  指定列名称
	 * @param fieldValue 指定列的值
	 * @param id         主键id
	 * @return 存在返回成功
	 */
	protected boolean existFieldName(String fieldName, Object fieldValue, Integer id) {
		QueryWrapper<T> queryWrapper = createFieldQueryWrapper(id, fieldName, fieldValue, false);
		return getBaseMapper().selectCount(queryWrapper) > 0;
	}

	/**
	 * 是否存在指定的列名称，表中有状态名称
	 *
	 * @param fieldName  指定列名称
	 * @param fieldValue 指定列的值
	 * @param id         主键id
	 * @return 存在返回成功
	 */
	protected boolean existFieldNameWithStatus(String fieldName, Object fieldValue, Integer id) {
		QueryWrapper<T> queryWrapper = createFieldQueryWrapper(id, fieldName, fieldValue, true);
		return getBaseMapper().selectCount(queryWrapper) > 0;
	}

	protected Optional<T> selectByFieldName(String fieldName, Object fieldValue, Integer id) {
		QueryWrapper<T> queryWrapper = createFieldQueryWrapper(id, fieldName, fieldValue, false);
		return Optional.ofNullable(getBaseMapper().selectOne(queryWrapper));
	}

	protected Optional<T> selectByFieldNameWithStatus(String fieldName, Object fieldValue, Integer id) {
		QueryWrapper<T> queryWrapper = createFieldQueryWrapper(id, fieldName, fieldValue, true);
		return Optional.ofNullable(getBaseMapper().selectOne(queryWrapper));
	}

	private QueryWrapper<T> createFieldQueryWrapper(Integer id, String fieldName, Object fieldValue, boolean hasStatus) {

		QueryWrapper<T> queryWrapper = new QueryWrapper<>();
//		queryWrapper.ne(Objects.nonNull(id), ID_COLUMN, id).eq(fieldName, fieldValue).in(hasStatus, STATUS_COLUMN, DeletedEnum.NOT_DELETE);
		return queryWrapper;
	}

}
