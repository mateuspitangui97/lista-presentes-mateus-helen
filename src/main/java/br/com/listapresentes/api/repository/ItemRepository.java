package br.com.listapresentes.api.repository;

import br.com.listapresentes.api.entity.Item;
import br.com.listapresentes.api.entity.StatusItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByStatusOrderByNomeAsc(StatusItem status);

    List<Item> findAllByOrderByDataCriacaoDesc();
}
