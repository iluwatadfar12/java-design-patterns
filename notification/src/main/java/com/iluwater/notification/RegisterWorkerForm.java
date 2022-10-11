package com.iluwater.notification;

import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
public class RegisterWorkerForm {
  String name;
  String occupation;
  LocalDate dateOfBirth;

  RegisterWorkerDTO worker;

  RegisterWorkerService service = new RegisterWorkerService();

  /**
   *
   * @param name
   * @param occupation
   * @param dateOfBirth
   */
  public RegisterWorkerForm(String name, String occupation, LocalDate dateOfBirth) {
    this.name = name;
    this.occupation = occupation;
    this.dateOfBirth = dateOfBirth;
  }

  /**
   *
   */
  public void submit() {
    saveToClaim();
    service.registerWorker(worker);
    if (worker.getNotification().hasErrors()) {
      indicateErrors();
      LOGGER.info("Not registered, see errors");
    } else {
      LOGGER.info("Registration Succeeded");
    }
  }

  private void saveToClaim() {
    worker = new RegisterWorkerDTO();
    worker.setName(name);
    worker.setOccupation(occupation);
    worker.setDateOfBirth(dateOfBirth);
  }

  private void indicateErrors() {
    checkError(RegisterWorkerDTO.MISSING_NAME, name);
    checkError(RegisterWorkerDTO.MISSING_DOB, dateOfBirth.toString());
    checkError(RegisterWorkerDTO.MISSING_OCCUPATION, occupation);
    checkError(RegisterWorkerDTO.DOB_TOO_SOON, dateOfBirth.toString());
  }

  private void checkError(NotificationError error, String info) {
    if (worker.getNotification().getErrors().contains(error)) {
      showError(info, error.toString());
    }
  }

  void showError(String info, String message) {
    LOGGER.info(message + ": \"" + info + "\"");
  }
}
