package com.kennywgx.config.mybatisplus;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(IntegerList.class)
@Slf4j
public class IntegerListTypeHandler extends BaseTypeHandler<IntegerList> {

	private final static Logger logger = LoggerFactory.getLogger(IntegerListTypeHandler.class);

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, IntegerList parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, parameter.stream().map(String::valueOf).collect(Collectors.joining(",")));
	}

	@Override
	public IntegerList getNullableResult(ResultSet rs, String columnName) throws SQLException {
		try {
			return getIntegerList(rs.getString(columnName));
		} catch (Exception e) {
			log.error("字段解析错误, columnName: [{}]", columnName, e);
			return new IntegerList();
		}
	}

	@Override
	public IntegerList getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return getIntegerList(rs.getString(columnIndex));
	}

	@Override
	public IntegerList getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return getIntegerList(cs.getString(columnIndex));
	}

	private IntegerList getIntegerList(String string) throws SQLException {
		IntegerList result = new IntegerList();
		result.addAll(Arrays.stream(string.split(",")).map(Integer::parseInt).collect(Collectors.toList()));
		return result;
	}

}