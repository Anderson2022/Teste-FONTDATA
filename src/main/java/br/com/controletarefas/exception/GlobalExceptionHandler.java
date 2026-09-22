package br.com.controletarefas.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(Exception.class)
    public String tratar(Exception exception, Model model) {
        LOG.error("Erro não tratado", exception);
        model.addAttribute("mensagem", "Não foi possível concluir a operação. Tente novamente.");
        return "erro";
    }
}
