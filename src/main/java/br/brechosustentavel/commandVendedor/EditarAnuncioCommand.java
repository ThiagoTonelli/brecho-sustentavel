/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.commandVendedor;

import br.brechosustentavel.model.Anuncio;
import br.brechosustentavel.model.EventoLinhaDoTempo;
import br.brechosustentavel.model.Peca;
import br.brechosustentavel.observer.Observavel;
import br.brechosustentavel.presenter.ManterAnuncioPresenter.ManterAnuncioPresenter;
import br.brechosustentavel.repository.IAnuncioRepository;
import br.brechosustentavel.repository.IDefeitoPecaRepository;
import br.brechosustentavel.repository.IDefeitoRepository;
import br.brechosustentavel.repository.ILinhaDoTempoRepository;
import br.brechosustentavel.repository.IPecaRepository;
import br.brechosustentavel.repository.ITipoDePecaRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import br.brechosustentavel.service.AplicarDescontosDefeitosService;
import br.brechosustentavel.service.CalculadoraDeIndicesService;
import br.brechosustentavel.service.SessaoUsuarioService;
import br.brechosustentavel.view.IJanelaManterAnuncioView;
import java.awt.Component;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.swing.JCheckBox;
import br.brechosustentavel.repository.IComposicaoRepository;

/**
 *
 * @author thiag
 */
public class EditarAnuncioCommand implements ICommandVendedor{

    @Override
    public Object executar(ManterAnuncioPresenter presenter) {
        try {
            //carrega repositorios
            RepositoryFactory fabrica = RepositoryFactory.getInstancia();
            IPecaRepository repositoryPeca = fabrica.getPecaRepository();
            IAnuncioRepository repositoryAnuncio = fabrica.getAnuncioRepository();
            ILinhaDoTempoRepository repositoryLinhaDoTempo = fabrica.getLinhaDoTempoRepository();
            IDefeitoPecaRepository repositoryDefeitoPeca = fabrica.getDefeitoPecaRepository();
            IDefeitoRepository repositoryDefeito = fabrica.getDefeitoRepository();
            ITipoDePecaRepository repositoryTipoDePeca = fabrica.getTipoDePecaRepository();
            
            
            IJanelaManterAnuncioView view = presenter.getView();
            
            //captura valores da presenter
            String id_c = view.getTxtId_c().getText();
            String tipoPeca = (String) view.getSelectTipoDePeca().getSelectedItem();
            String subcategoria = view.getTxtSubcategoria().getText();
            String tamanho = (String) view.getTxtTamanho().getText();
            String cor = view.getTxtCor().getText();
            
            Map<String, Double> materiaisQuantidade = new HashMap<>();

            int quantidade1 = (int) view.getSpinnerComposicao().getValue();
            if (quantidade1 > 0) {
                materiaisQuantidade.put((String) view.getSelectComposicao().getSelectedItem(), (double) quantidade1);
            }

            int quantidade2 = (int) view.getSpinnerComposicao1().getValue();
            if (quantidade2 > 0) {
                materiaisQuantidade.put((String) view.getSelectComposicao1().getSelectedItem(), (double) quantidade2);
            }

            int quantidade3 = (int) view.getSpinnerComposicao2().getValue();
            if (quantidade3 > 0) {
                materiaisQuantidade.put((String) view.getSelectComposicao2().getSelectedItem(), (double) quantidade3);
            }

            
            double massaEstimada = Double.parseDouble(view.getTxtMassa().getText());
            String estadoDeConservacao = view.getTxtEstadoConservacao().getText();
            double precoBase = Double.parseDouble(view.getTxtPrecoBase().getText());
            
            
            //cria repositorio dos materiais
            IComposicaoRepository repositoryMaterial = fabrica.getComposicaoRepository();   
            //limpa materiais com quantidade na composicao = 0
            materiaisQuantidade.entrySet().removeIf(entry -> entry.getValue() <= 0.0);
            Set<String> chaves = materiaisQuantidade.keySet();
            List<String> listaChaves = new ArrayList<>(chaves);
            //pega apenas os materiais que foram realmente selecionados
            Map<String, Double> materiaisDesconto = repositoryMaterial.buscarMateriaisNome(listaChaves);
            
            
            //captura quais defeitos foram selecionados para a peca
            Map<String, Double> defeitosSelecionados = new HashMap<>();
            for (Component comp : view.getPainelScrollDefeitos().getComponents()) {
                if (comp instanceof JCheckBox) {
                    JCheckBox checkBox = (JCheckBox) comp;
                    if (checkBox.isSelected()) {
                        double desconto = (double) checkBox.getClientProperty("desconto");
                        defeitosSelecionados.put(checkBox.getText(), desconto);

                    }
                }
            }
            Optional<Peca> pecaOpt = repositoryPeca.consultar(id_c); 

            if(pecaOpt.isPresent()) {
                Peca peca = pecaOpt.get();
                peca.setId_c(id_c);
                Optional<EventoLinhaDoTempo> ultimoEventoOpt = repositoryLinhaDoTempo.ultimoEvento(id_c);
                EventoLinhaDoTempo ultimoEvento = ultimoEventoOpt.get();
                if (ultimoEvento.getTipoEvento().equals("encerrado")){
                    
                    peca.setSubcategoria(subcategoria);
                    peca.setTamanho(tamanho);
                    peca.setCor(cor);
                    peca.setMassaEstimada(massaEstimada);
                    peca.setEstadoDeConservacao(estadoDeConservacao);
                    peca.setPrecoBase(precoBase);
                    peca.setDefeitos(defeitosSelecionados);
                    peca.setMaterialDesconto(materiaisDesconto);
                    peca.setMaterialQuantidade(materiaisQuantidade);
                    
                    //excluir defeitos antigos
                    repositoryDefeitoPeca.excluirDefeitosDaPeca(peca.getId_c());
                    
                    
                    List<Integer> idsDefeitos = new ArrayList<>();
                    for(String defeito : defeitosSelecionados.keySet()){
                        Integer id = repositoryDefeito.buscarIdPeloNomeDoDefeito(defeito);
                        if(id != null){ 
                            idsDefeitos.add(id); 
                        }
                    }
                    
                    repositoryDefeitoPeca.adicionarVariosDefeitosAPeca(peca.getId_c(), idsDefeitos);
                    int idTipo = repositoryTipoDePeca.buscarIdTipo(tipoPeca);
                    peca.setTipoDePeca(tipoPeca);
                    peca.setIdTipoDePeca(idTipo);
                    
                    //calcula metricas e preco final
                    AplicarDescontosDefeitosService aplicarDescontos = new AplicarDescontosDefeitosService();
                    peca.setPrecoFinal(aplicarDescontos.calcularDescontos(peca));
                    CalculadoraDeIndicesService calcularIndices = new CalculadoraDeIndicesService();
                    double gwpAvoided = calcularIndices.calcularGwpAvoided(peca);
                    double gwpBase = calcularIndices.calcularGwpBase(peca);
                    double mciPeca = calcularIndices.calcularMCI(peca);
                    LocalDateTime data = LocalDateTime.now();

                    
                    EventoLinhaDoTempo evento = new EventoLinhaDoTempo("Publicação", "publicação", data, gwpAvoided, mciPeca);
                    evento.setCliclo(ultimoEvento.getCiclo_n());
                    List<EventoLinhaDoTempo> eventosLinha = new ArrayList<>();
                    eventosLinha.add(evento);
                    peca.setLinhaDoTempo(eventosLinha);
                    repositoryPeca.editar(peca);
                    SessaoUsuarioService sessao = SessaoUsuarioService.getInstancia();
                    Anuncio anuncio = new Anuncio(sessao.getUsuarioAutenticado().getId(), peca, peca.getPrecoFinal(), gwpAvoided, mciPeca);
                    repositoryAnuncio.editar(anuncio);
                    
                    Observavel.getInstance().notifyObservers();
                    return anuncio;
                }
                else {
                    //ta tentando editar depois de vender
                }
            }

        } catch (NumberFormatException e) {
            throw new RuntimeException("Erro de formato numérico. Verifique se os campos de preço, massa, etc., estão preenchidos corretamente.", e);
        } catch (Exception ex) {
            throw new RuntimeException("Ocorreu um erro ao criar o anúncio: " + ex.getMessage(), ex);
        }
        return null;
    }
    
}
