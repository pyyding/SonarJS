/*
 * SonarQube JavaScript Plugin
 * Copyright (C) 2011-2018 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.javascript.metrics;


import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.issue.NoSonarFilter;
import org.sonar.javascript.visitors.JavaScriptFileImpl;
import org.sonar.plugins.javascript.api.tree.ScriptTree;
import org.sonar.plugins.javascript.api.visitors.DoubleDispatchVisitor;

public class NoSonarVisitor extends DoubleDispatchVisitor {

  private final NoSonarFilter noSonarFilter;
  private final boolean ignoreHeaderComments;

  public NoSonarVisitor(NoSonarFilter noSonarFilter, boolean ignoreHeaderComments) {
    this.noSonarFilter = noSonarFilter;
    this.ignoreHeaderComments = ignoreHeaderComments;
  }

  @Override
  public void visitScript(ScriptTree tree) {
    CommentLineVisitor commentVisitor = new CommentLineVisitor(tree, ignoreHeaderComments);

    InputFile inputFile = ((JavaScriptFileImpl) getContext().getJavaScriptFile()).inputFile();
    noSonarFilter.noSonarInFile(inputFile, commentVisitor.noSonarLines());
  }
}
