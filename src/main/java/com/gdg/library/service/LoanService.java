package com.gdg.library.service;

import com.gdg.library.model.Book;
import com.gdg.library.model.Loan;
import com.gdg.library.model.Member;
import com.gdg.library.repository.BookRepository;
import com.gdg.library.repository.LoanRepository;
import com.gdg.library.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public LoanService(LoanRepository loanRepository, BookRepository bookRepository, MemberRepository memberRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    public Loan loanBook(Long memberId, Long bookId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

        if (!book.isAvailable()) {
            throw new RuntimeException("Book is not available for loan");
        }

        book.setAvailable(false); // 대출 불가능 상태로 변경
        bookRepository.save(book);

        Loan loan = Loan.builder()
                .book(book)
                .member(member)
                .loanDate(LocalDate.now().toString())
                .build();
        return loanRepository.save(loan);
    }

    public Loan returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new RuntimeException("Loan not found"));
        Book book = loan.getBook();

        book.setAvailable(true); // 반납 후 대출 가능 상태로 변경
        bookRepository.save(book);

        loan.setReturnDate(LocalDate.now().toString());
        return loanRepository.save(loan);
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }
}
