package br.com.fatec.catalogo.repositories;

import br.com.fatec.catalogo.models.AuditoriaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuditoriaRepository extends JpaRepository<AuditoriaModel, Long> {
    List<AuditoriaModel> findAllByOrderByDataHoraDesc();
}
