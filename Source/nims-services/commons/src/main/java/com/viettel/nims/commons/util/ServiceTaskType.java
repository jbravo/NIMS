package com.viettel.nims.commons.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ServiceTaskType {

  private static boolean isChanelService(String serviceType) {
    return serviceType.equals(Constant.TASK.SERVICCE_TYPE_OW) || serviceType
        .equals(Constant.TASK.SERVICCE_TYPE_MW) || serviceType
        .equals(Constant.TASK.SERVICCE_TYPE_LEADLINE);
  }

  public static boolean isMultiPointService(String serviceType) {
    return isChanelService(serviceType) || isTranmissionService(serviceType);
  }

  private static boolean isTranmissionService(String serviceType) {
    return serviceType.equals(Constant.TASK.SERVICCE_TYPE_PCM) || serviceType
        .equals(Constant.TASK.SERVICCE_TYPE_WHITE_CHANNEL);
  }

  public static boolean isBccsShop(Long cmVersion) {
    return cmVersion != null && cmVersion.equals(Constant.TASK.CM_VERSION_BCCS_SHOP);
  }

  public static boolean isInfraFCN(Long technology) {
    return Constant.INFRA_TYPE.FCN.equals(technology);
  }

  public static boolean isTaskDeploy(Long taskType) {
    return taskType.equals(Constant.TASK.TASK_TYPE_SETUP) || taskType
        .equals(Constant.TASK.TASK_TYPE_CHANGE_ADD_AFTER_INSTALLING) || taskType
        .equals(Constant.TASK.TASK_TYPE_DEPLOY_CHANGE_SPEED);
  }

  public static boolean isSynStatusSale(Long taskType) {
    return isTaskDeploy(taskType) || taskType.equals(Constant.TASK.TASK_TYPE_DEPLOY_CHANGE_DEVICE);
  }

  public static boolean isResultSuccessIM(String responseCode) {
    return Constant.TASK.IM_RETURN_SUCCESS.equals(responseCode)
        || Constant.TASK.IM_RETURN_SUCCESS_NOT_FOUND.equals(responseCode);
  }

  public static boolean isSurveyHotline(Long reqType) {
    return Constant.TASK.TASK_TYPE_SURVEY_HOTLINE.equals(reqType);
  }

  public static boolean isMultiscreenAON(Long technology, String serviceCode) {
    return isMultiscreen(serviceCode) && isInfraFCN(technology);
  }

  public static boolean isMultiscreen(String serviceCode) {
    return Constant.TASK.SERVICCE_TYPE_MULTISCREEN_2.equals(serviceCode)
        || Constant.TASK.SERVICCE_TYPE_MULTI_SCREEN.equals(serviceCode);
  }

  public static boolean isChangeONU(Long paramChangeDevice) {
    return Constant.CHANGE_DEVICE_SALE.CHANGE_ONU.equals(paramChangeDevice);
  }

  public static boolean isChangeSTB(Long paramChangeDevice) {
    return Constant.CHANGE_DEVICE_SALE.CHANGE_STB.equals(paramChangeDevice);
  }

  public static boolean isAmpService(String service) {
    return service.equals(Constant.TASK.SERVICCE_TYPE_THC)
        || service.equals(Constant.TASK.SERVICCE_TYPE_MULTI_SCREEN);
  }

  public static boolean isInfraCCN(Long technology) {
    return Constant.INFRA_TYPE.CCN.equals(technology);
  }

  public static boolean isPstn(String serviceType) {
    return serviceType.equals(Constant.TASK.SERVICCE_TYPE_PSTN);
  }

  public static boolean isNextTV(String serviceType) {
    return serviceType.equals(Constant.TASK.SERVICCE_TYPE_IPTV);
  }

  public static boolean isPstnAfterSale(String service, Long taskType) {
    return isPstn(service) && isAfterSale(taskType);
  }

  public static boolean isActiveSubFail(Long progress) {
    return Constant.TASK.PROGRESS_ACTIVE_SUB_FAILSE.equals(progress)
        || Constant.TASK.PROGRESS_ACTIVE_DEVICE.equals(progress);
  }

  public static boolean isChangeDeployModel(Long taskType) {
    return Constant.TASK.TASK_TYPE_CHANGE_MODEL_GPON.equals(taskType);
  }

  public static boolean isChangeAddressCus(Long taskType) {
    return Constant.TASK.TASK_TYPE_CHANGE_ADD_AFTER_INSTALLING.equals(taskType);
  }

  public static boolean isPcmService(String serviceType) {
    return serviceType.equals(Constant.TASK.SERVICCE_TYPE_PCM);
  }

  public static boolean isInfraCCN(String infraType) {
    return Constant.TASK.INFRA_TYPE_CCN_STR.equals(infraType);
  }

  public static boolean isInfraFCN(String infraType) {
    return Constant.TASK.INFRA_TYPE_FCN_STR.equals(infraType);
  }

  public static boolean isInfraGPON(Long technology) {
    return Constant.INFRA_TYPE.GPON.equals(technology);
  }

  public static boolean isAfterSale(Long taskType) {
    return Constant.TASK.TASK_TYPE_CHANGE_ADD_AFTER_INSTALLING.equals(taskType)
        || Constant.TASK.TASK_TYPE_CHANGE_REQUEST_SURVEY.equals(taskType)
        || Constant.TASK.TASK_TYPE_SURVEY_CHANGE_SPEED.equals(taskType)
        || Constant.TASK.TASK_TYPE_DEPLOY_CHANGE_SPEED.equals(taskType);
  }

  public static boolean isUplink(Long uplink) {
    return Constant.TASK.IS_UPLINK.equals(uplink);
  }

  public static boolean isPrivateInternationalChannelService(String serviceType) {
    return serviceType.equals(Constant.TASK.SERVICCE_TYPE_PRIVATE_INTERNATIONAL_CHANNEL);
  }

  public static boolean isWithdrawalTaskType(Long reqType) {
    return Constant.TASK.TASK_TYPE_SUPPRESS.equals(reqType)
        || Constant.TASK.WITHDRAW_DEVICE.equals(reqType)
        || Constant.TASK.WITHDRAW_DEVICE_WIRE.equals(reqType)
        || Constant.TASK.WITHDRAW_WIRE.equals(reqType);
  }

  public static boolean isVersionCC2(Long versionCC) {
    return Constant.TASK.CC_VERSION2_BCCS_SHOP.equals(versionCC);
  }

  public static boolean isIpphone(String serviceType) {
    return Constant.TASK.SERVICCE_TYPE_NGN.equals(serviceType);
  }

  public static boolean isConfig(Long changePstnToNgn) {
    return Constant.TASK.CONFIG.equals(changePstnToNgn);
  }

  public static boolean isTaskChangeDeviceFormSale(Long taskType) {
    return Constant.TASK.TASK_TYPE_DEPLOY_CHANGE_DEVICE.equals(taskType);
  }

  public static boolean isLL3L(String serviceType) {
    return Constant.TASK.SERVICCE_TYPE_LEADLINE.equals(serviceType);
  }

  public static boolean isInfraCATV(Long technology) {
    return Constant.INFRA_TYPE.CATV.equals(technology);
  }
}
