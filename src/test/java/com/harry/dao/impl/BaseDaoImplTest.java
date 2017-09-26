package com.harry.dao.impl;

import com.alibaba.fastjson.JSON;
import com.harry.data.PrivilegeTree;
import com.harry.entity.*;
import com.harry.utils.SetToListUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")

public class BaseDaoImplTest {


    @Resource
    BaseDaoImpl baseDaoImpl;


    @Resource
    SessionFactory sessionFactory;


    @Test

    public void testSaveHouseType() throws Exception {

        HouseType houseType = new HouseType();
        houseType.setType(HouseType.ONE_BEDROOM_ONE_LIVINGROOM);

        HouseType houseType2 = new HouseType();
        houseType2.setType(HouseType.ONE_BEDROOM_TWO_LIVINGROOM);

        HouseType houseType3 = new HouseType();
        houseType3.setType(HouseType.TWO_BEDROOM_ONE_LIVINGROOM);

        HouseType houseType4 = new HouseType();
        houseType4.setType(HouseType.TWO_BEDROOM_TWO_LIVINGROOM);


        List<HouseType> houseTypeList = new ArrayList<HouseType>();
        houseTypeList.add(houseType);
        houseTypeList.add(houseType2);
        houseTypeList.add(houseType3);
        houseTypeList.add(houseType4);

        baseDaoImpl.saveObjects(houseTypeList);


    }


    @Test
    public void testSaveDistrict() throws Exception {

        District district = new District();
        district.setName("海淀区");

        District district2 = new District();
        district2.setName("朝阳区");

        SubDistrict subDistrict = new SubDistrict();
        subDistrict.setName("朝阳路");
        subDistrict.setDistrict(district2);

        SubDistrict subDistrict2 = new SubDistrict();
        subDistrict2.setName("中关村大街");
        subDistrict2.setDistrict(district);

        SubDistrict subDistrict3 = new SubDistrict();
        subDistrict3.setName("知春路");
        subDistrict3.setDistrict(district);

        SubDistrict subDistrict4 = new SubDistrict();
        subDistrict4.setName("学院路");
        subDistrict4.setDistrict(district);

        List<SubDistrict> subDistrictList = new ArrayList<SubDistrict>();
        subDistrictList.add(subDistrict2);
        subDistrictList.add(subDistrict3);
        subDistrictList.add(subDistrict4);
        subDistrictList.add(subDistrict);


        baseDaoImpl.saveObjects(subDistrictList);

    }


    @Test
    public void testSaveCompany() throws Exception {

        Company company = new Company();
        company.setName("北京自如房地产经纪公司");
        company.setInnerNo("1002");
        company.setIsVerify(1);
        company.setContact("tom");
        company.setHotLine("010-11112222");
        company.setCellphone("13122223333");

        Session session = sessionFactory.openSession();

        Company merge = (Company) session.merge(company);

        session.beginTransaction().commit();


        System.out.println(merge.getId());

    }


    @Test
    public void testSaveRole() throws Exception {


        Role role = new Role();
        role.setRoleName("admin");
        role.setRoleDesc("admin");
        role.setIsValid(1);


        Privilege privilege = new Privilege();
        privilege.setPrivilegeName("用户管理");
        privilege.setIsValid(1);
        privilege.setPrivilegePath("/pages/privilege.jsp");

        RolePrivilege rolePrivilege = new RolePrivilege();
        //    rolePrivilege.setRole(role);
        rolePrivilege.setPrivilege(privilege);


        Set<RolePrivilege> rolePrivileges = new HashSet<RolePrivilege>();
        rolePrivileges.add(rolePrivilege);

        role.setRolePrivileges(rolePrivileges);
        //   privilege.setRolePrivileges(rolePrivileges);


        baseDaoImpl.saveObject(role);


    }


    @Test
    public void testSaveDepartment() throws Exception {

        Department department = new Department();

        department.setName("aa部");
        department.setIsValid(1);

        //  baseDaoImpl.saveObject(department);

        Session session = sessionFactory.openSession();

        Department merge = (Department) session.merge(department);

        session.beginTransaction().commit();

        System.out.println(merge.getDeptno());

    }


    @Test
    public void testSaveUser() throws Exception {

        Role role = baseDaoImpl.getObjectById(Role.class, 1);
        Department department = baseDaoImpl.getObjectById(Department.class, 2);

        SubDistrict subDistrict = baseDaoImpl.getObjectById(SubDistrict.class, 1);
        HouseType houseType = baseDaoImpl.getObjectById(HouseType.class, 1);

        House house = new House();
        house.setTitle("中关村大街小区房一房一厅");
        house.setArea(45.5);
        house.setPrice(2000.00);
        house.setSubDistrict(subDistrict);
        house.setHouseType(houseType);
        house.setIssueDate(new Date());
        house.setTelephone("13922223333");
        house.setIsValid(1);

        Set<House> houses = new HashSet<House>();
        houses.add(house);

        User user = new User();
        user.setUsername("harry");
        user.setPassword("11111");
        user.setRealname("harry");
        user.setRole(role);
        user.setCreateDate(new Date());
        user.setTelephone("13922223333");
        user.setIsValid(1);
        user.setDepartment(department);
        //  user.setHouses(houses);
        // house.setUser(user);

        baseDaoImpl.saveObject(user);

    }


    @Test
    public void testGetUser() throws Exception {

        User user = baseDaoImpl.getObjectById(User.class, 1);

        Set<RolePrivilege> rolePrivileges = user.getRole().getRolePrivileges();

        List<RolePrivilege> rolePrivilegeList = SetToListUtil.setToList(rolePrivileges);

        System.out.println(rolePrivilegeList);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", "harry");
        //params.put("password",  "11111");

        List<User> userList = baseDaoImpl.getObjectsByFields(User.class, params);

        System.out.println(userList);


    }


    @Test
    public void testGetHouseType() throws Exception {

        List<HouseType> houseTypeList = baseDaoImpl.getObjects(HouseType.class);

        System.out.println(houseTypeList);

    }


    @Test
    public void testSavePrivilege() throws Exception {

        Privilege privilege = new Privilege();
        privilege.setPrivilegeName("权限设置");
        privilege.setIsValid(1);
        privilege.setIsMenu(1);
        privilege.setPrivilegePath("/pages/privilege.jsp");

        baseDaoImpl.saveObject(privilege);

    }


    @Test
    public void testGetDistrict() throws Exception {

        List<District> districtList = baseDaoImpl.getObjects(District.class);

        System.out.println(districtList);


        // List<House> houseList = baseDaoImpl.getObjects(House.class);

        // System.out.println(houseList);


        House house = baseDaoImpl.getObjectById(House.class, 1);
        //System.out.println(house);


        Session currentSession = sessionFactory.openSession();

        House house1 = currentSession.get(House.class, 1);

        System.out.println(house1);


    }


    @Test
    public void testSaveOrder() throws Exception {


        User user = baseDaoImpl.getObjectById(User.class, 1);
        House house = baseDaoImpl.getObjectById(House.class, 1);

        System.out.println(user.getId());

        Order order = new Order();
        order.setUserId(user.getId());
        order.setCreateDate(new Date());
        order.setHouseId(house.getId());
        order.setIsValid(1);
        order.setStatus(Order.OBLIGATION);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setArea(house.getArea());
        orderDetail.setDeposit(house.getDeposit());
        orderDetail.setPrice(house.getPrice());
        orderDetail.setTitle(house.getTitle());

        order.getOrderDetails().add(orderDetail);

        baseDaoImpl.saveObject(order);

    }


    @Test
    public void testGetUserPrivilegeTree() throws Exception {

        User user = baseDaoImpl.getObjectById(User.class, 1);

        Set<RolePrivilege> rolePrivileges = user.getRole().getRolePrivileges();

        List<RolePrivilege> rolePrivilegeList = SetToListUtil.setToList(rolePrivileges);

        List<Privilege> privilegeList = new ArrayList<Privilege>();


        for (RolePrivilege rolePrivilege : rolePrivilegeList) {

            if (rolePrivilege.getPrivilege().getParentId() == 0) {

                privilegeList.add(rolePrivilege.getPrivilege());

            }

        }


        List<PrivilegeTree> privilegeTreeList = getPrivilegeTreeList(privilegeList);

        String jsonString = JSON.toJSONString(privilegeTreeList);

        System.out.println(jsonString);


    }


    private List<PrivilegeTree> getPrivilegeTreeList(List<Privilege> privileges) {


        List<PrivilegeTree> privilegeTreeList = new ArrayList<PrivilegeTree>();

        for (Privilege privilege : privileges) {

            //if ( privilege.getIsMenu() == 1) {

            PrivilegeTree privilegeTree = new PrivilegeTree();

            privilegeTree.setId(privilege.getPrivilegeId());
            privilegeTree.setText(privilege.getPrivilegeName());
            privilegeTree.setIsMenu(privilege.getIsMenu());

            //  这里privilege.getSubPrivileges()必须同时确认不为null而且元素个数不为0

            if (privilege.getSubPrivileges() != null && privilege.getSubPrivileges().size() != 0) {

                List<PrivilegeTree> subPrivilegeTreeList = getPrivilegeTreeList(SetToListUtil.setToList(privilege.getSubPrivileges()));
                privilegeTree.setChildren(subPrivilegeTreeList);

            }

            privilegeTreeList.add(privilegeTree);

        }

        // }

        return privilegeTreeList;
    }


}