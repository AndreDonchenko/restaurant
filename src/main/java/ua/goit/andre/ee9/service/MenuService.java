package ua.goit.andre.ee9.service;

import ua.goit.andre.ee9.dao.HMenuDao;
import ua.goit.andre.ee9.model.Menu;

import java.util.List;

/**
 * Created by Andre on 07.09.2016.
 */
public class MenuService {

    private HMenuDao menuDao;

    public List<Menu> getAllMenus() {
        return menuDao.getAll();
    }

    public void addMenu(Menu menu) {
        menuDao.add(menu);
    }

    public void delMenu(Menu menu) {
        menuDao.del(menu);
    }

    public void saveMenu(Menu menu) {
        menuDao.add(menu);
    }

    public Menu getMenuById(Integer id) {
        return menuDao.getById(id);
    }

    public void setMenuDao(HMenuDao menuDao) {
        this.menuDao = menuDao;
    }
}
