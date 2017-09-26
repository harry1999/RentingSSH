package com.harry.action;


import com.harry.data.Messager;
import com.harry.data.Result;
import com.harry.entity.House;
import com.harry.entity.Role;
import com.harry.entity.User;
import com.harry.service.CompanyService;
import com.harry.service.DistrictService;
import com.harry.service.HouseService;
import com.harry.service.RoleService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Component
public class CommonAction extends ActionSupport {


    private Result result;
    private Messager messager;
    private House house;
    private Integer tag;

    private File imageMain;
    private String imageMainFileName;
    private String imageMainContentType;

    private File[] imagesDetail;
    private String[] imagesDetailFileName;
    private String[] imagesDetailContentType;

    private int page;
    private int rows;

    private String searchHouseTitle;
    private Map<String, Object> housesInfo;

    private String searchRoleName;
    private Map<String, Object> rolesInfo;

    private Integer[] housesId;

    private Role role;
    private Integer[] privilegesId;

    private Integer[] rolesId;


    @Resource
    HouseService houseServiceImpl;


    @Resource
    RoleService roleServiceImpl;


    @Resource
    CompanyService companyServiceImpl;


    @Resource
    DistrictService districtServiceImpl;



    public String getHouseTypes() {

        result = houseServiceImpl.getHouseTypes();
        return "success";

    }


    public String getCompanyInfo() {

        result = companyServiceImpl.getCompanyInfo();

        return "success";

    }


    public String getDistricts() {

        result = districtServiceImpl.getDistricts( tag );

        return "success";

    }


    public String getHouses() {

        result = houseServiceImpl.getHouses( messager );

        return "success";

    }


    public String loadHouses() {

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("houseTitle", searchHouseTitle);
        queryParams.put("start", (page - 1) * rows);
        queryParams.put("limit", rows);

        housesInfo = houseServiceImpl.getHousesByLimit(queryParams);

        return "success";

    }


    public String loadRoles() {

        HttpSession session = ServletActionContext.getRequest().getSession();
        User currentUser = (User) session.getAttribute("loginUser");

        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("roleName", searchRoleName);
        queryParams.put("currentUser", currentUser);
        queryParams.put("start", (page - 1) * rows);
        queryParams.put("limit", rows);

        rolesInfo = roleServiceImpl.getRolesByLimit(queryParams);

        return "success";

    }


    public String onCreateHouse() {

        HttpSession session = ServletActionContext.getRequest().getSession();
        User currentUser = (User) session.getAttribute("loginUser");

        String imgSavePath = ServletActionContext.getServletContext().getRealPath("images/house/");

        result = houseServiceImpl.saveHouse(currentUser, house, imageMain, imageMainFileName, imagesDetail, imagesDetailFileName, imgSavePath);

        return "success";

    }


    public String onUpdateHouse() {

        String imgSavePath = ServletActionContext.getServletContext().getRealPath("images/house/");

        result = houseServiceImpl.updateHouse(house, imageMain, imageMainFileName, imagesDetail,
                imagesDetailFileName, imgSavePath);

        return "success";

    }


    public String onIssueHouse() {

        result = houseServiceImpl.issueHouses(housesId);

        return "success";

    }


    public String onUnShelveHouses() {

        result = houseServiceImpl.unShelveHouses(housesId);

        return "success";

    }


    public String onDelHouses() {

        result = houseServiceImpl.delHouses(housesId);

        return "success";

    }


    public String onCreateRole() {

        HttpSession session = ServletActionContext.getRequest().getSession();
        User currentUser = (User) session.getAttribute("loginUser");

        result = roleServiceImpl.createRole(role.getRoleName(), role.getRoleDesc(), currentUser.getRole(), privilegesId);

        return "success";

    }


    public String onUpdateRole() {

        result = roleServiceImpl.updateRole(role, privilegesId);

        return "success";

    }


    public String onCheckRolesCorrelation() {

        result = roleServiceImpl.checkRolesCorrelation(rolesId);

        return "success";


    }


    public String onDelRoles() {

        result = roleServiceImpl.delRoles(rolesId);

        return "success";

    }


    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public Messager getMessager() {
        return messager;
    }

    public void setMessager(Messager messager) {
        this.messager = messager;
    }


    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }


    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }


    public File getImageMain() {
        return imageMain;
    }

    public void setImageMain(File imageMain) {
        this.imageMain = imageMain;
    }

    public String getImageMainFileName() {
        return imageMainFileName;
    }

    public void setImageMainFileName(String imageMainFileName) {
        this.imageMainFileName = imageMainFileName;
    }

    public String getImageMainContentType() {
        return imageMainContentType;
    }

    public void setImageMainContentType(String imageMainContentType) {
        this.imageMainContentType = imageMainContentType;
    }

    public File[] getImagesDetail() {
        return imagesDetail;
    }

    public void setImagesDetail(File[] imagesDetail) {
        this.imagesDetail = imagesDetail;
    }

    public String[] getImagesDetailFileName() {
        return imagesDetailFileName;
    }

    public void setImagesDetailFileName(String[] imagesDetailFileName) {
        this.imagesDetailFileName = imagesDetailFileName;
    }

    public String[] getImagesDetailContentType() {
        return imagesDetailContentType;
    }

    public void setImagesDetailContentType(String[] imagesDetailContentType) {
        this.imagesDetailContentType = imagesDetailContentType;
    }

    public Map<String, Object> getHousesInfo() {
        return housesInfo;
    }

    public void setHousesInfo(Map<String, Object> housesInfo) {
        this.housesInfo = housesInfo;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getSearchHouseTitle() {
        return searchHouseTitle;
    }

    public void setSearchHouseTitle(String searchHouseTitle) {
        this.searchHouseTitle = searchHouseTitle;
    }

    public Integer[] getHousesId() {
        return housesId;
    }

    public void setHousesId(Integer[] housesId) {
        this.housesId = housesId;
    }

    public String getSearchRoleName() {
        return searchRoleName;
    }

    public void setSearchRoleName(String searchRoleName) {
        this.searchRoleName = searchRoleName;
    }

    public Map<String, Object> getRolesInfo() {
        return rolesInfo;
    }

    public void setRolesInfo(Map<String, Object> rolesInfo) {
        this.rolesInfo = rolesInfo;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Integer[] getPrivilegesId() {
        return privilegesId;
    }

    public void setPrivilegesId(Integer[] privilegesId) {
        this.privilegesId = privilegesId;
    }

    public Integer[] getRolesId() {
        return rolesId;
    }

    public void setRolesId(Integer[] rolesId) {
        this.rolesId = rolesId;
    }


}
