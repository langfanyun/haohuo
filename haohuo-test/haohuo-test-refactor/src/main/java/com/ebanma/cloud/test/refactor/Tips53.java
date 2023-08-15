package com.ebanma.cloud.test.refactor;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 浪翻云
 * @version $ Id: Tips53, v 0.1 2023/08/15 09:22 xlang Exp $
 */
public class Tips53 {

    public class Workspace {
        private String uniqueId;
        private String creator;
        private WorkspaceStatus workspaceStatus;

        public String getUniqueId() {
            return uniqueId;
        }
        public String getCreator() {
            return creator;
        }
        public WorkspaceStatus getWorkspaceStatus() {
            return workspaceStatus;
        }
    }

    public enum WorkspaceStatus {
        ERROR("error"),
        SUCCESS("success");
        private String result;
        WorkspaceStatus(String result) {
            this.result = result;
        }
        public String getResult() {
            return result;
        }
        public void setResult(String result) {
            this.result = result;
        }
    }

    /**
     * 获取用户所创建的workspace且状态为SUCCESS的列子
     * @param workspaces
     * @param creator
     * @return
     */
    public List<String> querySuccessWorkspaceUniqueId(List<Workspace> workspaces, String creator) {
        List<String> result = Lists.newArrayList();
        for (Workspace workspace : workspaces) {
            // 校验workspace的creator
            if (!StringUtils.equals(creator, workspace.getCreator())) {
                continue;
            }
            // 判断workspace的状态是否为SUCCESS
            if (!WorkspaceStatus.SUCCESS.equals(workspace.getWorkspaceStatus())) {
                continue;
            }
            String uniqueId = workspace.getUniqueId();
            // 判断uniqueId是否为null
            if (uniqueId == null) {
                continue;
            }
            result.add(uniqueId);
        }
        return result;
    }

    /**
     * 使用管道替换循环后，可读性大大提升
     * @param workspaces
     * @param creator
     * @return
     */
    public List<String> querySuccessWorkspaceUniqueId_refactor(List<Workspace> workspaces, String creator) {
        return workspaces.stream()
                // 校验workspace的creator
                .filter(w -> StringUtils.equals(creator, w.getCreator()))
                // 判断workspace的状态是否为SUCCESS
                .filter(w -> WorkspaceStatus.SUCCESS.equals(w.getWorkspaceStatus()))
                .map(Workspace::getUniqueId)
                // 判断uniqueId是否为null
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}