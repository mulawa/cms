package pl.com.bottega.cms.domain;

import pl.com.bottega.cms.domain.commands.CommandInvalidException;
import pl.com.bottega.cms.domain.commands.CreateReservationCommand;
import pl.com.bottega.cms.domain.commands.ValidationErrors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "cinema_halls")
public class CinemaHall {

    private static final int ROWS = 10;
    private static final int SEATS = 15;

    @Id
    @GeneratedValue
    private Long id;

    private boolean[][] seats = new boolean[ROWS][SEATS];

    public CinemaHall() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean[][] getSeats() {
        return seats;
    }

    public void setSeats(boolean[][] seats) {
        this.seats = seats;
    }

    public CinemaHall(Set<Reservation> currentReservations) {
        for (Reservation reservation : currentReservations) {
            Set<Seat> reservedSeats = reservation.getSeats();
            for (Seat seat : reservedSeats) {
                seats[seat.getRow()][seat.getSeat()] = true;
            }
        }
    }

    public void checkReservation(CreateReservationCommand command) {
        checkSeatsAvailability(command.getSeats());
        ckeckSeatNumbers(command.getSeats());
    }

    private void ckeckSeatNumbers(Set<Seat> seats) {
        //TODO sprawdzenie czy miejsca są obok siebie
    }

    private void checkSeatsAvailability(Set<Seat> commandSeats) {
        for (Seat seat : commandSeats) {
            Integer row = seat.getRow();
            Integer seatNo = seat.getSeat();
            if (seats[row - 1][seatNo - 1]) {
                ValidationErrors errors = new ValidationErrors();
                errors.add("seats", "Seat no " + seatNo + " in row " + row + " is already reserved ");
                throw new CommandInvalidException(errors);
            }
        }
    }


}
