/*
 * Copyright (C) 2020 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.android.accessibility.talkback.training;

import androidx.annotation.StringRes;
import com.google.android.accessibility.talkback.training.NavigationButtonBar.ButtonType;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A Training consists of {@link PageConfig}s and Buttons. By default, the buttons in the list are
 * available in every page except Exit button. If {@link #isExitButtonOnlyShowOnLastPage()} is true,
 * Exit button is shown only on the last page.
 *
 * <p>For example: Creating a training with two pages and three buttons, back, next and exit.
 *
 * <pre>{@code
 * Training.builder(TrainingName.TUTORIAL)
 *         .setTitle(R.string.title)
 *         .setPages(
 *             ImmutableList.of(
 *                WELCOME_PAGE,
 *                NEW_GESTURE_PAGE))
 *         .setButtons(
 *             ImmutableList.of(BUTTON_TYPE_BACK, BUTTON_TYPE_NEXT, BUTTON_TYPE_EXIT)
 *         .build();
 * }</pre>
 */
@AutoValue
public abstract class TrainingConfig {

  /** Unique identifiers for training. */
  public enum TrainingId {
    TRAINING_ID_ON_BOARDING_TALKBACK_13_0,
    TRAINING_ID_ON_BOARDING_TALKBACK_13_0_PRE_R,
    TRAINING_ID_ON_BOARDING_FOR_MULTIFINGER_GESTURES,
    TRAINING_ID_TUTORIAL_FOR_WATCH,
    TRAINING_ID_FIRST_RUN_TUTORIAL_FOR_GESTURE_NAVIGATION_USER,
    TRAINING_ID_FIRST_RUN_TUTORIAL_FOR_GESTURE_NAVIGATION_USER_PRE_R,
    TRAINING_ID_FIRST_RUN_TUTORIAL_FOR_3_BUTTON_NAVIGATION_USER,
    TRAINING_ID_FIRST_RUN_TUTORIAL_FOR_3_BUTTON_NAVIGATION_USER_PRE_R,
    TRAINING_ID_TUTORIAL_FOR_GESTURE_NAVIGATION_USER,
    TRAINING_ID_TUTORIAL_FOR_GESTURE_NAVIGATION_USER_PRE_R,
    TRAINING_ID_TUTORIAL_FOR_3_BUTTON_NAVIGATION_USER,
    TRAINING_ID_TUTORIAL_FOR_3_BUTTON_NAVIGATION_USER_PRE_R,
    TRAINING_ID_TUTORIAL_PRACTICE_GESTURE,
    TRAINING_ID_TUTORIAL_PRACTICE_GESTURE_PRE_R,
    TRAINING_ID_VOICE_COMMAND_HELP,
    TRAINING_ID_VOICE_COMMAND_HELP_FOR_WATCH,
  }

  public static @Nullable TrainingConfig getTraining(TrainingId trainingId) {
    switch (trainingId) {
      case TRAINING_ID_ON_BOARDING_TALKBACK_13_0:
        return OnboardingInitiator.ON_BOARDING_TALKBACK_13_0;
      case TRAINING_ID_ON_BOARDING_TALKBACK_13_0_PRE_R:
        return OnboardingInitiator.ON_BOARDING_TALKBACK_13_0_PRE_R;
      case TRAINING_ID_ON_BOARDING_FOR_MULTIFINGER_GESTURES:
        return OnboardingInitiator.ON_BOARDING_FOR_MULTIFINGER_GESTURES;
      case TRAINING_ID_TUTORIAL_FOR_WATCH:
        return TutorialInitiator.TUTORIAL_FOR_WATCH;
      case TRAINING_ID_FIRST_RUN_TUTORIAL_FOR_GESTURE_NAVIGATION_USER:
        return TutorialInitiator.FIRST_RUN_TUTORIAL_FOR_GESTURE_NAVIGATION_USER;
      case TRAINING_ID_FIRST_RUN_TUTORIAL_FOR_GESTURE_NAVIGATION_USER_PRE_R:
        return TutorialInitiator.FIRST_RUN_TUTORIAL_FOR_GESTURE_NAVIGATION_USER_PRE_R;
      case TRAINING_ID_FIRST_RUN_TUTORIAL_FOR_3_BUTTON_NAVIGATION_USER:
        return TutorialInitiator.FIRST_RUN_TUTORIAL_FOR_3_BUTTON_NAVIGATION_USER;
      case TRAINING_ID_FIRST_RUN_TUTORIAL_FOR_3_BUTTON_NAVIGATION_USER_PRE_R:
        return TutorialInitiator.FIRST_RUN_TUTORIAL_FOR_3_BUTTON_NAVIGATION_USER_PRE_R;
      case TRAINING_ID_TUTORIAL_FOR_GESTURE_NAVIGATION_USER:
        return TutorialInitiator.TUTORIAL_FOR_GESTURE_NAVIGATION_USER;
      case TRAINING_ID_TUTORIAL_FOR_GESTURE_NAVIGATION_USER_PRE_R:
        return TutorialInitiator.TUTORIAL_FOR_GESTURE_NAVIGATION_USER_PRE_R;
      case TRAINING_ID_TUTORIAL_FOR_3_BUTTON_NAVIGATION_USER:
        return TutorialInitiator.TUTORIAL_FOR_3_BUTTON_NAVIGATION_USER;
      case TRAINING_ID_TUTORIAL_FOR_3_BUTTON_NAVIGATION_USER_PRE_R:
        return TutorialInitiator.TUTORIAL_FOR_3_BUTTON_NAVIGATION_USER_PRE_R;
      case TRAINING_ID_TUTORIAL_PRACTICE_GESTURE:
        return TutorialInitiator.TUTORIAL_PRACTICE_GESTURE;
      case TRAINING_ID_TUTORIAL_PRACTICE_GESTURE_PRE_R:
        return TutorialInitiator.TUTORIAL_PRACTICE_GESTURE_PRE_R;
      case TRAINING_ID_VOICE_COMMAND_HELP:
        return VoiceCommandHelpInitiator.VOICE_COMMAND_HELP;
      case TRAINING_ID_VOICE_COMMAND_HELP_FOR_WATCH:
        return VoiceCommandHelpInitiator.VOICE_COMMAND_HELP_FOR_WATCH;
      default:
        return null;
    }
  }

  private int totalPageNumber = -1;

  @StringRes
  public abstract int getName();

  public abstract ImmutableList<PageConfig> getPages();

  /** Returns a list of {@link ButtonType}s which will be shown on the training. */
  public abstract ImmutableList<Integer> getButtons();

  public abstract boolean isExitButtonOnlyShowOnLastPage();

  public abstract boolean isSupportNavigateUpArrow();

  /** The total number of the page where page number information is shown. */
  public int getTotalPageNumber() {
    if (totalPageNumber < 0) {
      totalPageNumber = 0;
      for (PageConfig pageConfig : getPages()) {
        if (pageConfig.showPageNumber()) {
          totalPageNumber++;
        } else {
          break;
        }
      }
    }
    return totalPageNumber;
  }

  public static Builder builder(@StringRes int trainingName) {
    return new Builder(trainingName);
  }

  private static TrainingConfig create(
      @StringRes int name,
      ImmutableList<PageConfig> pages,
      ImmutableList<Integer> buttons,
      boolean isExitButtonOnlyShowOnLastPage,
      boolean isSupportNavigateUpArrow) {
    return new AutoValue_TrainingConfig(
        name, pages, buttons, isExitButtonOnlyShowOnLastPage, isSupportNavigateUpArrow);
  }

  /** Builder for Training. */
  public static class Builder {
    @StringRes private final int trainingName;
    private List<PageConfig> pages = new ArrayList<>();
    private ImmutableList<Integer> buttons;
    private boolean isExitButtonOnlyShowOnLastPage = false;
    private boolean isSupportNavigateUpArrow = false;

    private Builder(@StringRes int trainingName) {
      this.trainingName = trainingName;
    }

    /** Adds pages into the training. */
    public Builder addPages(PageConfig.Builder... pageBuilders) {
      for (PageConfig.Builder pageBuilder : pageBuilders) {
        pages.add(pageBuilder.build());
      }
      return this;
    }

    /** Adds a page which is an end page of a section. */
    public Builder addPageEndOfSection(PageConfig.Builder pageBuilder) {
      return addPage(
          pageBuilder,
          /* hasNavigationButtonBar= */ true,
          /* showPageNumber= */ true,
          /* isEndOfSection= */ true);
    }

    /** Adds a page which is no page number information and no navigation bar, like index page. */
    public Builder addPageWithoutNumberAndNavigationBar(PageConfig.Builder pageBuilder) {
      return addPage(
          pageBuilder,
          /* hasNavigationButtonBar= */ false,
          /* showPageNumber= */ false,
          /* isEndOfSection= */ false);
    }

    /**
     * Adds a page into the training.
     *
     * @param hasNavigationButtonBar if the navigation button bar is shown on the page or not.
     *     Default is true.
     * @param showPageNumber if the page number is shown on the page or not. Default is true.
     * @param isEndOfSection if the page is an end page of a section. Default is false.
     */
    public Builder addPage(
        PageConfig.Builder pageBuilder,
        boolean hasNavigationButtonBar,
        boolean showPageNumber,
        boolean isEndOfSection) {
      if (!hasNavigationButtonBar) {
        pageBuilder = pageBuilder.hideNavigationButtonBar();
      }
      if (!showPageNumber) {
        pageBuilder = pageBuilder.hidePageNumber();
      }
      if (isEndOfSection) {
        pageBuilder = pageBuilder.setEndOfSection();
      }
      pages.add(pageBuilder.build());
      return this;
    }

    /** Adds all pages into the training. */
    public Builder setPages(List<PageConfig.Builder> pageBuilders) {
      for (PageConfig.Builder pageBuilder : pageBuilders) {
        this.pages.add(pageBuilder.build());
      }
      return this;
    }

    /** Sets a list of {@link ButtonType}s which will be shown on the training. */
    public Builder setButtons(ImmutableList<Integer> buttons) {
      this.buttons = buttons;
      return this;
    }

    /** Sets true if the exit button is shown only on the last page. */
    public Builder setExitButtonOnlyShowOnLastPage(boolean isExitButtonOnlyShowOnLastPage) {
      this.isExitButtonOnlyShowOnLastPage = isExitButtonOnlyShowOnLastPage;
      return this;
    }

    /** Sets true if the training requires a navigate up arrow on the action bar. */
    public Builder setSupportNavigateUpArrow(boolean isSupportNavigateUpArrow) {
      this.isSupportNavigateUpArrow = isSupportNavigateUpArrow;
      return this;
    }

    public TrainingConfig build() {
      return TrainingConfig.create(
          trainingName,
          ImmutableList.copyOf(pages),
          buttons,
          isExitButtonOnlyShowOnLastPage,
          isSupportNavigateUpArrow);
    }
  }
}
