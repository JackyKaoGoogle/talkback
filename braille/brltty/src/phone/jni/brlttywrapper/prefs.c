/*
 * Copyright (C) 2022 Google Inc.
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

/*
 * Stubs for brltty preferences that are accessed from Android.
 * TODO: Consider making these adjustable by the user in the BrailleBack UI.
 */

#include "prologue.h"  // NOLINT Must include first
#include "prefs.h"
#include "defaults.h"

Preferences prefs = {
    .autoreleaseTime = DEFAULT_AUTORELEASE_TIME,
    .autorepeatInterval = 30,  // 30 centiseconds = 300 ms
    .autorepeatEnabled = 1,
    .autorepeatPanning = DEFAULT_AUTOREPEAT_PANNING,
    .longPressTime = DEFAULT_LONG_PRESS_TIME,
    .brailleDisplayOrientation = DEFAULT_BRAILLE_DISPLAY_ORIENTATION};
