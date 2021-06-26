package com.example.vaccines.repository.vaccine;

import com.example.vaccines.model.vaccine.Vaccine;
import com.example.vaccines.model.vaccine.VaccineType;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class VaccineRepositoryImpl implements VaccineRepository{

    private static final String SELECT_ALL  = "SELECT id, research_name, manufacturer_name, vaccine_type, required_number_of_shots, available_number_of_shots FROM vaccine ";

    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert inserter;

    public VaccineRepositoryImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.inserter = new SimpleJdbcInsert(jdbc)
                .withTableName("vaccine")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Vaccine> findAll() {
        List<Vaccine> vaccines = new ArrayList<>();
        try {
            vaccines.addAll(jdbc.query(SELECT_ALL, this::mapRowToVaccine));
            return vaccines;
        } catch(EmptyResultDataAccessException e) {
            return vaccines;
        }
    }

    @Override
    public Optional<Vaccine> findVaccineByResearchName(String researchName) {
        try {
            return Optional.ofNullable(
                    jdbc.queryForObject(SELECT_ALL + "WHERE research_name = ?", this::mapRowToVaccine, researchName)
            );
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Vaccine> findVaccineByType(String vaccineType) {
        List<Vaccine> vaccines = new ArrayList<>();
        try {
            vaccines.addAll(jdbc.query(SELECT_ALL + "WHERE vaccine_type = ?", this::mapRowToVaccine, vaccineType));
            return vaccines;
        } catch(EmptyResultDataAccessException e) {
            return vaccines;
        }
    }

    @Override
    public List<Vaccine> findVaccineBySuffix(String suffix) {
        List<Vaccine> vaccines = new ArrayList<>();
        try {
            vaccines.addAll(jdbc.query(SELECT_ALL + "WHERE RIGHT(manufacturer_name, LENGTH(?)) ILIKE ?", this::mapRowToVaccine, suffix, suffix));
            return vaccines;
        } catch(EmptyResultDataAccessException e) {
            return vaccines;
        }
    }

    @Override
    public Optional<Vaccine> save(Vaccine vaccine) {
        try {
            vaccine.setId(saveVaccineDetails(vaccine));
            return Optional.of(vaccine);
        } catch (DuplicateKeyException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Vaccine> update(Vaccine vaccine) {
        int executed = jdbc.update("UPDATE vaccine SET " +
                        "manufacturer_name = ?, " +
                        "vaccine_type = ?, " +
                        "required_number_of_shots = ?, " +
                        "available_number_of_shots = ? " +
                        "WHERE research_name = ?",
                vaccine.getManufacturerName(),
                vaccine.getVaccineType().toString(),
                vaccine.getRequiredNumberOfShots(),
                vaccine.getAvailableNumberOfShots(),
                vaccine.getResearchName());

        if(executed > 0) {
            return Optional.of(vaccine);
        } else {
            return Optional.empty();
        }
    }

    /**
     * TODO - Replace with @Transactional later
     *
     * @param researchName
     */

    @Override
    public void deleteByResearchName(String researchName) {

        Connection conn;

        try {
            conn = jdbc.getDataSource().getConnection();
            conn.setAutoCommit(false);
        } catch(SQLException e) {
            e.printStackTrace();
            return;
        }

        Optional<Vaccine> v = findVaccineByResearchName(researchName);

        if(v.isPresent()) {
            jdbc.update("DELETE FROM side_effect WHERE vaccine_id = ?", v.get().getId());
            jdbc.update("DELETE FROM side_effect WHERE research_name = ?", researchName);
        } else {
            return;
        }

        try {
            conn.commit();
            conn.setAutoCommit(false);
        } catch(SQLException e) {
            e.printStackTrace();
            return;
        }

    }

    private Vaccine mapRowToVaccine(ResultSet rs, int rowNum) throws SQLException {
        return new Vaccine(
                rs.getLong("id"),
                rs.getString("research_name"),
                rs.getString("manufacturer_name"),
                VaccineType.valueOf(rs.getString("vaccine_type")),
                rs.getInt("required_number_of_shots"),
                rs.getInt("available_number_of_shots")
        );
    }

    private long saveVaccineDetails(Vaccine vaccine) {
        Map<String, Object> values = new HashMap<>();

        values.put("research_name", vaccine.getResearchName());
        values.put("manufacturer_name", vaccine.getManufacturerName());
        values.put("vaccine_type", vaccine.getVaccineType().toString());
        values.put("required_number_of_shots", vaccine.getRequiredNumberOfShots());
        values.put("available_number_of_shots", vaccine.getAvailableNumberOfShots());

        return inserter.executeAndReturnKey(values).longValue();
    }
}
