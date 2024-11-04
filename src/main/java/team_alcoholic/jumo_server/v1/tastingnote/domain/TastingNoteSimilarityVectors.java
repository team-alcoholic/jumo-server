package team_alcoholic.jumo_server.v1.tastingnote.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "tasting_note_similarity_vectors_v1")
public class TastingNoteSimilarityVectors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tastingnote1;
    private String tastingnote2;
    private Double similarity;


}