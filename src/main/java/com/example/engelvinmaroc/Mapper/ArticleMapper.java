package com.example.engelvinmaroc.Mapper;

import com.example.engelvinmaroc.DTO.ArticleDTO;
import com.example.engelvinmaroc.DTO.Article_CommandeDTO;
import com.example.engelvinmaroc.entities.Article;
import com.example.engelvinmaroc.entities.Article_Commande;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {
    private ModelMapper modelMapper=new ModelMapper();


    public ArticleDTO fromArticle(Article article){
        return  modelMapper.map(article, ArticleDTO.class);
    }
    public  Article fromArticleDTO(ArticleDTO articleDTO){
        return  modelMapper.map(articleDTO, Article.class);
    }

}
