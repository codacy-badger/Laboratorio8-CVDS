package edu.eci.cvds.sampleprj.dao.mybatis;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.eci.cvds.sampleprj.dao.ItemDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.TipoItemDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.TipoItemMapper;
import edu.eci.cvds.samples.entities.TipoItem;
import org.mybatis.guice.transactional.Transactional;

import java.sql.SQLException;
import java.util.List;

public class MyBATISTipoItemDAO implements TipoItemDAO {

    @Inject
    private TipoItemMapper tipoItemMapper;

    @Transactional
    @Override
    public void save(String des) throws PersistenceException{
        try{
            tipoItemMapper.addTipoItem(des);
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("Error al registrar tipo item con la descripcion"+ des ,e);
        }
    }

    @Override
    public TipoItem load(int id) throws PersistenceException {
        try{
            return tipoItemMapper.getTipoItem(id);
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("Error al consultar el tipo item con id "+id,e);
        }
    }

    @Override
    public List<TipoItem> loadTipoItems() throws PersistenceException {
        try{
            return tipoItemMapper.getTiposItems();
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("Error al consultar tipo items",e);
        }
    }





}