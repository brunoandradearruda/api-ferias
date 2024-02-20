package com.seplagpb.apiferiasseplagpb.view;
import com.seplagpb.apiferiasseplagpb.model.UnidadeDeTrabalho;
import com.seplagpb.apiferiasseplagpb.service.UnidadeDeTrabalhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/unidades") // Base path para todas as ações deste controlador
public class UnidadeDeTrabalhoViewController {

    private final UnidadeDeTrabalhoService unidadeDeTrabalhoService;

    @Autowired
    public UnidadeDeTrabalhoViewController(UnidadeDeTrabalhoService unidadeDeTrabalhoService) {
        this.unidadeDeTrabalhoService = unidadeDeTrabalhoService;
    }

    @GetMapping
    public String listarUnidades(Model model) {
        model.addAttribute("unidades", unidadeDeTrabalhoService.listarTodos());
        return "unidades";
    }

    // Adicione aqui métodos para adicionar, editar e excluir
    @GetMapping("/nova")
    public String mostrarFormularioDeNovaUnidade(Model model) {
        model.addAttribute("unidade", new UnidadeDeTrabalho());
        return "unidade-formulario";
    }

    // Processar o formulário de nova unidade
    @PostMapping("/salvar")
    public String salvarUnidade(@ModelAttribute("unidade") UnidadeDeTrabalho unidade) {
        unidadeDeTrabalhoService.salvarOuAtualizar(unidade);
        return "redirect:/unidades";
    }

    // Mostrar formulário para editar uma unidade existente
    @GetMapping("/editar/{id}")
    public String mostrarFormularioDeEdicao(@PathVariable Long id, Model model) {
        UnidadeDeTrabalho unidade = unidadeDeTrabalhoService.encontrarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido:" + id));
        model.addAttribute("unidade", unidade);
        return "unidade-formulario";
    }

    // Excluir uma unidade
    @GetMapping("/excluir/{id}")
    public String excluirUnidade(@PathVariable Long id) {
        unidadeDeTrabalhoService.excluir(id);
        return "redirect:/unidades";
    }

}
