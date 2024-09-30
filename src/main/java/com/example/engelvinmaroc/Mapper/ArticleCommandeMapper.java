package com.example.engelvinmaroc.Mapper;

import com.example.engelvinmaroc.DTO.Article_CommandeDTO;
import com.example.engelvinmaroc.entities.Article_Commande;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ArticleCommandeMapper {
    private ModelMapper modelMapper=new ModelMapper();

    public Article_CommandeDTO fromArticleCommande(Article_Commande article_commande){
        return  modelMapper.map(article_commande, Article_CommandeDTO.class);
    }
    public  Article_Commande fromArticleCommandeDTO(Article_CommandeDTO article_commandeDTO){
        return  modelMapper.map(article_commandeDTO, Article_Commande.class);
    }
}
