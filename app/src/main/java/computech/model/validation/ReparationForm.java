package computech.model.validation;

import computech.model.Article;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 *
 * Validates a reparation request form.
 *
 */

public class ReparationForm {

        @NotNull(message = "Bitte wählen Sie einen Artikel aus.")
        private Article article;

        @NotEmpty(message = "Der Beschreibung darf nicht leer sein.")
        private String description;

    public void setArticle(Article article) {
        this.article = article;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /*public void setModel(String model) {
        this.model = model;
    } */

    public Article getArticle() {

        return article;
    }

    public String getDescription() {
        return description;
    }

    /* public String getModel() {
        return model;
    } */
}
