package edu.eci.arsw.exam.remote;

import edu.eci.arsw.exam.FachadaPersistenciaOfertas;
import edu.eci.arsw.exam.Oferta;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ManejadorOfertasSkeleton extends UnicastRemoteObject implements ManejadorOfertasStub {

    private FachadaPersistenciaOfertas fachadaPersistenciaOfertas;

    public ManejadorOfertasSkeleton() throws RemoteException {
        super();
    }

    public void setFachadaPersistenciaOfertas(FachadaPersistenciaOfertas fachadaPersistenciaOfertas) {
        this.fachadaPersistenciaOfertas = fachadaPersistenciaOfertas;
    }

    @Override
    public boolean agregarOferta(String productCode, Oferta oferta) throws RemoteException {
        return fachadaPersistenciaOfertas.agregarOferta(productCode, oferta);
    }
}