Feature: Mailchimp Sign Up

  Scenario Outline: Enter information to register a new user account
    Given I have opened my "<browser>" and I'm on the MailChimp sign up page
    Given I have entered my "<emailadress>" and my "<username>" and my "<password>"
    When I click the Sign Up button
    Then I have "<succeeded>" to register

    Examples:
      | browser | emailadress     | username                                                                                                  | password | succeeded |
      | brave   | brave@test1.now |                                                                                                           | lowUP1$! | yes       |
      | edge    | edge@test1.now  |                                                                                                           | lowUP1$! | yes       |
      | brave   | brave@test2.now | brave1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890 | lowUP1$! | no        |
      | edge    | edge@test2.now  | edge1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890  | lowUP1$! | no        |
      | brave   | brave@test3.now | admin                                                                                                     | lowUP1$! | no        |
      | edge    | edge@test3.now  | admin                                                                                                     | lowUP1$! | no        |
      | brave   |                 |                                                                                                           | lowUP1$! | no        |
      | edge    |                 |                                                                                                           | lowUP1$! | no        |

  # <browser> defines which browser is used
  # <emailadress>
    # Test 1 checks if