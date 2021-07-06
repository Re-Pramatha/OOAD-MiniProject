package client;

import club.Equipment;
import club.Game_Field;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

public class Reservation {

    private final String id;
    private final User user;
    private final Equipment equipment;
    private Date reserve_date;
    private final Date reserve_duration;
    private final long total_price;
    private final Date submit_date;
    private boolean paid;

    // TODO: add validators to reserve_date, reserve_duration
    public Reservation(User user, Equipment equipment, Date reserve_date, Date reserve_duration) {
        this.id = "R-" + UUID.randomUUID().toString();
        this.user = user;
        this.equipment = equipment;
        if (equipment instanceof Game_Field) {
            this.reserve_date = reserve_date;
        } else {
            this.reserve_date = new Date();
        }
        this.reserve_duration = reserve_duration;

        GregorianCalendar g = new GregorianCalendar();
        g.setTime(reserve_duration);
        int hours = g.get(Calendar.HOUR);
        float minutes = (float)(g.get(Calendar.MINUTE)) / 60;
        long price = (long) ((hours + minutes) * equipment.getPrice());
        price -= price % 100;
        this.total_price = price;

        this.submit_date = new Date();
        this.paid = false;
    }

    public String getId() { return this.id; }

    public User getUser() { return this.user; }

    public Equipment getEquipment() { return this.equipment; }

    public Date getReserve_date() { return this.reserve_date; }

    public Date getReserve_duration() { return this.reserve_duration; }

    public long getTotal_price() { return this.total_price; }

    public Date getSubmit_date() { return this.submit_date; }

    public boolean isPaid() { return this.paid; }

    public void pay() {
        if (!isPaid()) {
            User user = getUser();
            user.increase_debt(getTotal_price());
            this.paid = true;
        }
    }

    private boolean check_duration(Date duration) {
        GregorianCalendar g = new GregorianCalendar();
        g.setTime(duration);
        int hours = g.get(Calendar.HOUR);
        int minutes = g.get(Calendar.MINUTE) + hours * 60;
        return minutes <= (6 * 60);
    }

    public boolean has_passed() {
        Date now = new Date();
        Date reserve_date = getReserve_date();
        return now.after(reserve_date);
    }

    public void change_reserve_date(Date d) {
        if (getEquipment() instanceof Game_Field && !has_passed()) {
            Date now = new Date();
            if (now.before(d)) {
                long temp = d.getTime();
                this.reserve_date.setTime(temp);
            }
        }
    }

    public String toString() {
        String id = getId();
        String user = getUser().toString();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        String date = date_format.format(getSubmit_date());
        return String.format("<Reservation [id:%s]-[user:%s]-[submit date:%s]>", id, user, date);
    }

}
