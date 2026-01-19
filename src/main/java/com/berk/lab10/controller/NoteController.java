package com.berk.lab10.controller;

import com.berk.lab10.dto.NoteRequest;
import com.berk.lab10.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public String list(Model model, Authentication auth) {
        model.addAttribute("notes", noteService.getMyNotes(auth));
        return "notes-list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("note", new NoteRequest());
        return "note-form";
    }

    @PostMapping
    public String create(
            @Valid @ModelAttribute("note") NoteRequest note,
            BindingResult result,
            Authentication auth
    ) {
        if (result.hasErrors()) {
            return "note-form";
        }
        noteService.createNote(note, auth);
        return "redirect:/notes";
    }

    @GetMapping("/{id}/edit")
    public String editForm(
            @PathVariable Integer id,
            Model model,
            Authentication auth
    ) {
        var n = noteService.findMyNote(id, auth);
        NoteRequest req = new NoteRequest();
        req.setTitle(n.getTitle());
        req.setContent(n.getContent());

        model.addAttribute("note", req);
        model.addAttribute("noteId", id);
        return "note-form";
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable Integer id,
            @Valid @ModelAttribute("note") NoteRequest note,
            BindingResult result,
            Authentication auth
    ) {
        if (result.hasErrors()) {
            return "note-form";
        }
        noteService.updateNote(id, note, auth);
        return "redirect:/notes";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id, Authentication auth) {
        noteService.deleteNote(id, auth);
        return "redirect:/notes";
    }
}
