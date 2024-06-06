package app.senaistock.stock_senai.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import app.senaistock.stock_senai.Model.Cargos;
import app.senaistock.stock_senai.Model.Responsaveis;
import app.senaistock.stock_senai.Repository.CargosRepository;
import app.senaistock.stock_senai.Repository.ResponsaveisRepository;
import app.senaistock.stock_senai.Repository.SalasRepository;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResponsavelController {
    @Autowired
    private ResponsaveisRepository responsavelRepository;

    @Autowired
    private CargosRepository cargosRepository;

    @Autowired
    private SalasRepository salasRepository;

    boolean acessoResponsavel = false; // Definir o estado do login do usuário (logado/não logado)

    private String email;

    @GetMapping("/interna-responsavel")
    public String acessoPaginaInternaResponsavel(Model model) {
        String vaiPara = "";
        String responsavelEmail = email;
        Responsaveis responsaveis = responsavelRepository.findByEmail(responsavelEmail);
        if (acessoResponsavel) {
            model.addAttribute("nome", responsaveis.getNome_responsavel());
            model.addAttribute("foto", responsaveis.getFoto());
            Cargos cargo = responsaveis.getId_cargo();
            if (cargo != null) {
                model.addAttribute("cargo", cargo.getNome_cargo());
            } else {
                model.addAttribute("cargo", "Cargo não encontrado!");
            }

            vaiPara = "interna/interna-responsavel";
        } else {
            vaiPara = "redirect:/login-responsavel";
        }
        return vaiPara;
    }

    @PostMapping("acesso-responsavel")
    public String acessoResponsavel(@RequestParam String email, @RequestParam String senha, Model model) {
        this.email = email;
        try {
            boolean verificaEmail = responsavelRepository.existsByEmail(email);
            boolean verificaSenha = responsavelRepository.findByEmail(email).getSenha().equals(senha);
            String url = "";
            if (verificaEmail && verificaSenha) {
                acessoResponsavel = true;
                url = "redirect:/interna-responsavel";
            } else {
                model.addAttribute("mensagem", "erro ao realizar o login.");
                url = "redirect:/login-responsavel";
            }
            return url;
        } catch (Exception e) {
            model.addAttribute("mensagem", "algo deu errado!");
            return "redirect:/login-responsavel";
        }
    }

    @PostMapping("cadastro-responsavel")
    public String cadastrarResponsavelBanco(Responsaveis responsavel, Model model) {
        try {
            responsavelRepository.save(responsavel); // cadastro um obj responsavel no banco de dados
            model.addAttribute("mensagem",
                    "O Responsavel " + responsavel.getNome_responsavel() + " foi cadastrado com Sucesso!");
        } catch (Exception e) {
            model.addAttribute("mensagem", "Erro ao cadastrar responsavel. Por favor, tente novamente.");
        }
        return "/cadastro/cadastro-responsavel";
    }

    @GetMapping("/cadastro-responsavel")
    public String acessoPageInternaCadastroResponsavel(Model model) {
        String vaiPara = "";
        if (acessoResponsavel) {
            List<Cargos> cargos = cargosRepository.findAll();
            model.addAttribute("cargos", cargos);
            vaiPara = "cadastro/cadastro-responsavel";
        } else {
            vaiPara = "redirect:/login-responsavel";
        }
        return vaiPara;
    }

    @GetMapping("/listar-salas-p-responsaveis")
    public String getListaSalaResponsavel(Model model) {

        String url = "";
        if (responsavelRepository.existsByEmail(email)) {
            acessoResponsavel = true;
            Long valor_retornado = responsavelRepository.findByEmail(email).getId_responsavel();

            model.addAttribute("salas", salasRepository.findById_responsavel(valor_retornado));
            url = "redirect:/interna-view-salas";
        }
        return url;
    }

    @GetMapping("/logout-responsavel")
    public String logoutResponsavel() {
        acessoResponsavel = false;
        return "redirect:/login-responsavel";
    }

}
