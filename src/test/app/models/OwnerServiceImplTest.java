//package app.models;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.inject.Inject;
//
//import static org.junit.Assert.assertTrue;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "/context.xml" })
//@TransactionConfiguration(defaultRollback = true)
//@Transactional
//public class OwnerServiceImplTest {
//
//  @Inject
//  OwnerService service;
//
//  @Inject
//  OwnerDAO dao;
//
//  OwnerBean bean;
//
//  @Before
//  public void setUp() throws Exception {
//    bean = new OwnerBean();
//    bean.setFirstName("John");
//    bean.setLastName("Doe");
//    bean.setSsn("123-456-7890");
//    dao.save(bean);
//  }
//
//  @Test
//  public void testAddOwner() {
//    bean = new OwnerBean();
//    bean.setFirstName("Jane");
//    bean.setLastName("Doe");
//    bean.setSsn("098-765-4321");
//    service.addOwner("Jane", "Doe", "098-765-4321", null, null);
//    assertEquals(2, dao.findAll().size());
//    assertTrue(dao.findAll().contains(bean));
//  }
//
//  @Test
//  public void testGetOwners() {
//    assertEquals(1, service.getAllOwners().size());
//    assertEquals(bean, service.getAllOwners().get(0));
//  }
//
//  @Test
//  public void testDeleteOwner() {
//    OwnerBean bean = dao.findAll().get(0);
//    service.deleteOwner(bean.getId());
//    assertEquals(0, dao.findAll().size());
//  }
//
//}