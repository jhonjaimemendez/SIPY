package edu.JASoft.prueba;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

 class DialogExample extends Dialog {
  DialogExample(Shell parent) {
    super(parent);
  }

  public String open() {
    Shell parent = getParent();
    Shell dialog = new Shell(parent, SWT.DIALOG_TRIM
        | SWT.APPLICATION_MODAL);
    dialog.setSize(100, 100);
    dialog.setText("Java Source and Support");
    dialog.open();
    Display display = parent.getDisplay();
    while (!dialog.isDisposed()) {
      if (!display.readAndDispatch())
        display.sleep();
    }
    return "After Dialog";
  }

  public static void main(String[] argv) {
    new DialogExample(new Shell());
  }
}