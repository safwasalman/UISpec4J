package org.uispec4j;

import org.uispec4j.assertion.Assertion;
import org.uispec4j.assertion.dependency.InternalAssert;

import javax.swing.*;

/**
 * Base class for button-like components (toggle buttons, check boxes, etc.)
 */
public abstract class AbstractButton extends AbstractUIComponent {
  private javax.swing.AbstractButton abstractButton;

  protected AbstractButton(javax.swing.AbstractButton abstractButton) {
    this.abstractButton = abstractButton;
  }

  public void click() {
    InternalAssert.assertTrue("The button is not enabled, it cannot be activated",
                              abstractButton.isEnabled());
    doClick(abstractButton);
  }

  static void doClick(javax.swing.AbstractButton button) {
    ButtonModel model = button.getModel();
    model.setArmed(true);
    model.setPressed(true);
    model.setPressed(false);
    model.setArmed(false);
  }

  public Assertion textEquals(final String text) {
    return new Assertion() {
      public void check() {
        InternalAssert.assertEquals(text, abstractButton.getText().trim());
      }
    };
  }

  /**
   * Checks the icon displayed by the component. Please note that equals()
   * not being defined for Icon implementations, you will have to provide a pointer
   * to the actual Icon instance that is used in the production code. This make
   * this method mostly suited to unit testing.
   */
  public Assertion iconEquals(final Icon expected) {
    return new Assertion() {
      public void check() throws Exception {
        Icon actual = abstractButton.getIcon();
        if (expected != null) {
          InternalAssert.assertNotNull("The component contains no icon.", actual);
        }
        InternalAssert.assertSame("The icon ", expected, actual);
      }
    };
  }

  public String getLabel() {
    return abstractButton.getText();
  }

  public Trigger triggerClick() {
    return new Trigger() {
      public void run() {
        AbstractButton.this.click();
      }
    };
  }
}