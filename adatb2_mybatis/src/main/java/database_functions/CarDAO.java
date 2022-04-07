package database_functions;

import model.Car;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

public class CarDAO {

    public void insert(Car car) {
        SqlSession session = MyBatisUtil.getFactory().openSession();
        session.insert("CarMapper.insertCar", car);
        session.commit();
        session.close();
    }

    public List<Car> selectAll() {
        List<Car> carList;
        SqlSession session = MyBatisUtil.getFactory().openSession();
        carList = session.selectList("CarMapper.selectAllCar");
        session.commit();
        session.close();
        return carList;
    }

    public void deleteById(int id) {
        SqlSession session = MyBatisUtil.getFactory().openSession();
        session.delete("CarMapper.deleteCar", id);
        session.commit();
        session.close();
    }

    public void updateById(Car car) {
        SqlSession session = MyBatisUtil.getFactory().openSession();
        session.update("CarMapper.updateCar", car);
        session.commit();
        session.close();
    }

}
