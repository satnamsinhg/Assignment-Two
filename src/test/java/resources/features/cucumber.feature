Feature: Test For Rest API

  Scenario:  Test Post Request
    Given Send Post Http Request on /api/orders with Order
    Then Order should add into Order List

  Scenario:  Test Delete Request
    Given Send Delete Http Request on /api/orders with OrderId
    Then Order should delete from Order List
