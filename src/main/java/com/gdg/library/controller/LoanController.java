package com.gdg.library.controller;

import com.gdg.library.model.Loan;
import com.gdg.library.service.LoanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/loan/{memberId}/{bookId}")
    public Loan loanBook(@PathVariable Long memberId, @PathVariable Long bookId) {
        return loanService.loanBook(memberId, bookId);
    }

    @PostMapping("/return/{loanId}")
    public Loan returnBook(@PathVariable Long loanId) {
        return loanService.returnBook(loanId);
    }

    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }
}
