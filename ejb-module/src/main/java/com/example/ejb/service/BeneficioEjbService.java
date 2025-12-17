package com.example.ejb.service;

import com.example.ejb.entity.Beneficio;
import com.example.ejb.entity.dto.BeneficioDto;
import com.example.ejb.exceptions.DataIntegratyViolationException;
import com.example.ejb.exceptions.ObjectNotFoundException;
import com.example.ejb.repositories.BeneficioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BeneficioEjbService {

    @PersistenceContext
    private EntityManager em;

    private BeneficioRepository repository;

    public BeneficioEjbService(BeneficioRepository repository) {
        this.repository = repository;
    }

//    public void transfer(Long fromId, Long toId, BigDecimal amount) {
//        Beneficio from = em.find(Beneficio.class, fromId);
//        Beneficio to   = em.find(Beneficio.class, toId);
//
//        // BUG: sem validações, sem locking, pode gerar saldo negativo e lost update
//        from.setValor(from.getValor().subtract(amount));
//        to.setValor(to.getValor().add(amount));
//
//        em.merge(from);
//        em.merge(to);
//    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void transfer(Long fromId, Long toId, BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DataIntegratyViolationException("Valor da transferência inválido");
        }

        Beneficio from = em.find(
                Beneficio.class,
                fromId,
                LockModeType.OPTIMISTIC
        );

        Beneficio to = em.find(
                Beneficio.class,
                toId,
                LockModeType.OPTIMISTIC
        );

        if (from == null || to == null) {
            throw new DataIntegratyViolationException("Benefício não encontrado");
        }

        if (from.getValor().compareTo(amount) < 0) {
            throw new DataIntegratyViolationException("Saldo insuficiente");
        }

        from.setValor(from.getValor().subtract(amount));
        to.setValor(to.getValor().add(amount));

//        em.merge(from);
//        em.merge(to);
    }

    public Beneficio findByNome(String nome) {
        Optional<Beneficio> obj = repository.findByNome(nome);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public List<Beneficio> findAll() {
        return repository.findAll();
    }

    public Beneficio create(BeneficioDto obj) {
        findByNome(obj);

        Beneficio registro = new Beneficio();
        registro.setNome(obj.getNome());
        registro.setDescricao(obj.getDescricao());
        registro.setValor(obj.getValor());
        registro.setAtivo(Boolean.valueOf(obj.getAtivo()));
        registro.setVersion(Long.valueOf(obj.getVersion()));
        return repository.save(registro);
    }

    public Beneficio upDate(BeneficioDto obj, Integer id) {
        findByNome(obj);
        Beneficio registro = em.createQuery(
                        "SELECT b FROM Beneficio b WHERE b.id = :id",
                        Beneficio.class
                )
                .setParameter("id", id)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse(null);

        registro.setNome(obj.getNome());
        registro.setDescricao(obj.getDescricao());
        registro.setValor(obj.getValor());
        registro.setAtivo(Boolean.valueOf(obj.getAtivo()));
        registro.setVersion(Long.valueOf(obj.getVersion()));


        return repository.save(registro);
    }

    public void delete(Integer id) {
        repository.deleteById(Long.valueOf(id));
    }

    private void findByNome(BeneficioDto obj) {
        Optional<Beneficio> beneficioOptional = repository.findByNome(obj.getNome());
        if(beneficioOptional.isPresent() && !beneficioOptional.get().getId().equals(obj.getId())) {
            throw new DataIntegratyViolationException("Beneficio já cadastrado no sistema");
        }
    }

}
