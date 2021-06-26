package com.example.vaccines.repository.sideEffect;

import com.example.vaccines.model.sideEffect.SideEffect;
import com.example.vaccines.model.vaccine.Vaccine;
import com.example.vaccines.model.vaccine.VaccineType;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class SideEffectRepositoryImpl implements SideEffectRepository{

    private static final String SELECT_ALL  = "SELECT id, short_description, long_description, priority FROM side_effect ";

    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert inserter;

    public SideEffectRepositoryImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.inserter = new SimpleJdbcInsert(jdbc)
                .withTableName("side_effect")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<SideEffect> findAll() {
        List<SideEffect> sideEffects = new ArrayList<>();
        try {
            sideEffects.addAll(jdbc.query(SELECT_ALL, this::mapRowToSideEffect));
            return sideEffects;
        } catch(EmptyResultDataAccessException e) {
            return sideEffects;
        }
    }

    @Override
    public Optional<SideEffect> save(SideEffect sideEffect) {
        try {
            sideEffect.setId(saveSideEffectDetails(sideEffect));
            return Optional.of(sideEffect);
        } catch(DuplicateKeyException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<SideEffect> findByShortDescription(String shortDescription) {
        try {
            return Optional.ofNullable(
                    jdbc.queryForObject(SELECT_ALL + "WHERE shortDescription = ?", this::mapRowToSideEffect, shortDescription)
            );
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<SideEffect> findByLongDescription(String longDescription) {
        List<SideEffect> sideEffects = new ArrayList<>();
        try {
            sideEffects.addAll(jdbc.query(SELECT_ALL + "WHERE longDescription ILIKE CONCAT('%',?,'%')", this::mapRowToSideEffect, longDescription));
            return sideEffects;
        } catch(EmptyResultDataAccessException e) {
            return sideEffects;
        }
    }


    private SideEffect mapRowToSideEffect(ResultSet rs, int rowNum) throws SQLException {
        return new SideEffect(
                rs.getLong("id"),
                rs.getString("short_description"),
                rs.getString("long_description")
        );
    }


    private long saveSideEffectDetails(SideEffect sideEffect) {
        Map<String, Object> values = new HashMap<>();

        values.put("short_description", sideEffect.getShortDescription());
        values.put("long_description", sideEffect.getLongDescription());

        return inserter.executeAndReturnKey(values).longValue();
    }
}
