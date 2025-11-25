package com.carrental.dao.jdbc;

import com.carrental.dao.BookingDao;
import com.carrental.model.Booking;
import com.carrental.model.Car;
import com.carrental.model.Customer;
import com.carrental.util.DbUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookingDaoJdbc implements BookingDao {

    @Override
    public Booking save(Booking booking) {
        String insert = "INSERT INTO bookings (car_id, customer_id, start_date, end_date, total_amount, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        String update = "UPDATE bookings SET car_id=?, customer_id=?, start_date=?, end_date=?, total_amount=?, status=? WHERE id=?";

        try (Connection conn = DbUtil.getConnection()) {
            if (booking.getId() == 0) {
                try (PreparedStatement ps = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, booking.getCar().getId());
                    ps.setInt(2, booking.getCustomer().getId());
                    ps.setDate(3, Date.valueOf(booking.getStartDate()));
                    ps.setDate(4, Date.valueOf(booking.getEndDate()));
                    ps.setDouble(5, booking.getTotalAmount());
                    ps.setString(6, booking.getStatus());
                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) booking.setId(rs.getInt(1));
                    }
                }
            } else {
                try (PreparedStatement ps = conn.prepareStatement(update)) {
                    ps.setInt(1, booking.getCar().getId());
                    ps.setInt(2, booking.getCustomer().getId());
                    ps.setDate(3, Date.valueOf(booking.getStartDate()));
                    ps.setDate(4, Date.valueOf(booking.getEndDate()));
                    ps.setDouble(5, booking.getTotalAmount());
                    ps.setString(6, booking.getStatus());
                    ps.setInt(7, booking.getId());
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booking;
    }

    @Override
    public Optional<Booking> findById(Integer id) {
        String sql = "SELECT * FROM bookings WHERE id=?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Booking> findAll() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM bookings WHERE id=?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Booking> findByCustomerId(int customerId) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE customer_id=?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Booking mapRow(ResultSet rs) throws SQLException {
        Booking b = new Booking();
        b.setId(rs.getInt("id"));

        Car car = new Car();
        car.setId(rs.getInt("car_id"));
        b.setCar(car);

        Customer c = new Customer();
        c.setId(rs.getInt("customer_id"));
        b.setCustomer(c);

        Date start = rs.getDate("start_date");
        Date end = rs.getDate("end_date");
        b.setStartDate(start != null ? start.toLocalDate() : null);
        b.setEndDate(end != null ? end.toLocalDate() : null);

        b.setTotalAmount(rs.getDouble("total_amount"));
        b.setStatus(rs.getString("status"));
        return b;
    }
}
