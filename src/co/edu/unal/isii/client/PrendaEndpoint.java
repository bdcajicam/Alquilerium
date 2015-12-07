package co.edu.unal.isii.client;

import co.edu.unal.isii.PMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

@Api(name = "prendaendpoint", namespace = @ApiNamespace(ownerDomain = "edu.co", ownerName = "edu.co", packagePath = "unal.isii.client") )
public class PrendaEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listPrenda")
	public CollectionResponse<Prenda> listPrenda(@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		PersistenceManager mgr = null;
		Cursor cursor = null;
		List<Prenda> execute = null;

		try {
			mgr = getPersistenceManager();
			Query query = mgr.newQuery(Prenda.class);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				HashMap<String, Object> extensionMap = new HashMap<String, Object>();
				extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
				query.setExtensions(extensionMap);
			}

			if (limit != null) {
				query.setRange(0, limit);
			}

			execute = (List<Prenda>) query.execute();
			cursor = JDOCursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Prenda obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Prenda> builder().setItems(execute).setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getPrenda")
	public Prenda getPrenda(@Named("id") String id) {
		PersistenceManager mgr = getPersistenceManager();
		Prenda prenda = null;
		try {
			prenda = mgr.getObjectById(Prenda.class, id);
		} finally {
			mgr.close();
		}
		return prenda;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param prenda the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertPrenda")
	public Prenda insertPrenda(Prenda prenda) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (containsPrenda(prenda)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.makePersistent(prenda);
		} finally {
			mgr.close();
		}
		return prenda;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param prenda the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updatePrenda")
	public Prenda updatePrenda(Prenda prenda) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (!containsPrenda(prenda)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.makePersistent(prenda);
		} finally {
			mgr.close();
		}
		return prenda;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removePrenda")
	public void removePrenda(@Named("id") String id) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			Prenda prenda = mgr.getObjectById(Prenda.class, id);
			mgr.deletePersistent(prenda);
		} finally {
			mgr.close();
		}
	}

	private boolean containsPrenda(Prenda prenda) {
		PersistenceManager mgr = getPersistenceManager();
		boolean contains = true;
		try {
			mgr.getObjectById(Prenda.class, prenda.getId());
		} catch (javax.jdo.JDOObjectNotFoundException ex) {
			contains = false;
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}

}
