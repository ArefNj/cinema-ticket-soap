package org.soap.cinema.cinematicketsoap.controller;

import org.soap.cinema.cinematicketsoap.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {

    private final TicketService ticketService;

    public WebController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("movies", ticketService.getMovies().getMovies());
        return "index";
    }

    @GetMapping("/showtimes")
    public String showTimes(@RequestParam String movieId, Model model) {
        model.addAttribute("showTimes", ticketService.getShowTimes(movieId).getShowTimes());
        model.addAttribute("movieId", movieId);
        return "showtimes";
    }

    @GetMapping("/reserve")
    public String reserveForm(@RequestParam String movieId,
                              @RequestParam String showTimeId,
                              Model model) {
        model.addAttribute("movieId", movieId);
        model.addAttribute("showTimeId", showTimeId);
        return "reserve";
    }

    @PostMapping("/reserve")
    public String reserveSeat(@RequestParam String movieId,
                              @RequestParam String showTimeId,
                              @RequestParam int seatNumber,
                              @RequestParam String customerName,
                              Model model) {
        try {
            var response = ticketService.reserveSeat(movieId, showTimeId, seatNumber, customerName);
            model.addAttribute("reservationId", response.getReservationId());
            model.addAttribute("status", response.getStatus());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "result";
    }

    @GetMapping("/reservation")
    public String getReservation(@RequestParam String reservationId, Model model) {
        try {
            var reservation = ticketService.getReservation(reservationId);
            model.addAttribute("reservation", reservation);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "reservation";
    }

    @PostMapping("/cancel")
    public String cancelReservation(@RequestParam String reservationId, Model model) {
        try {
            var response = ticketService.cancelReservation(reservationId);
            model.addAttribute("reservationId", response.getReservationId());
            model.addAttribute("status", response.getStatus());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "result";
    }
}