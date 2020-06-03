package io.aetherit.ats.ws.repository.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BooleanOneZeroTypeHandler extends BaseTypeHandler<Boolean> {
    @Override
    public Boolean getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return convert(cs.getInt(columnIndex)) ;
    }

    @Override
    public Boolean getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return convert(rs.getInt(columnIndex));
    }

    @Override
    public Boolean getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return convert(rs.getInt(columnName));
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int columnIndex, Boolean param, JdbcType jdbcType) throws SQLException {
        ps.setInt(columnIndex, convert(param));
    }

    private int convert(Boolean b) {
        return b ? 1 : 0;
    }

    private Boolean convert(int s) {
        return (Integer.valueOf(s) != null && s==1);
    }
}
