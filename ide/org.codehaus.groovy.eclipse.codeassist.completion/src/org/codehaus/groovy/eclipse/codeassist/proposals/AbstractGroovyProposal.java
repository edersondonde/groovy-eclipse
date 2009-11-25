 /*
 * Copyright 2003-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.codehaus.groovy.eclipse.codeassist.proposals;

import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.PropertyNode;
import org.eclipse.jdt.core.CompletionProposal;
import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jdt.internal.ui.JavaPluginImages;
import org.eclipse.jdt.internal.ui.viewsupport.ImageDescriptorRegistry;
import org.eclipse.jdt.ui.text.java.CompletionProposalLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.objectweb.asm.Opcodes;

/**
 * @author Andrew Eisenberg
 * @created Nov 12, 2009
 *
 */
public abstract class AbstractGroovyProposal implements IGroovyProposal {
    
    private final static ImageDescriptorRegistry registry= JavaPlugin.getImageDescriptorRegistry();
    

    protected Image getImage(CompletionProposal proposal, CompletionProposalLabelProvider labelProvider) {
        return registry.get(labelProvider.createImageDescriptor(proposal));
    }

    protected Image getImageFor(ASTNode node) {
        if (node instanceof FieldNode) {
            int mods = ((FieldNode) node).getModifiers();
            if (test(mods, Opcodes.ACC_PUBLIC)) {
                return JavaPluginImages.get(JavaPluginImages.IMG_FIELD_PUBLIC);
            } else if (test(mods, Opcodes.ACC_PROTECTED)) {
                return JavaPluginImages.get(JavaPluginImages.IMG_FIELD_PROTECTED);
            } else if (test(mods, Opcodes.ACC_PRIVATE)) {
                return JavaPluginImages.get(JavaPluginImages.IMG_FIELD_PRIVATE);
            }
            return JavaPluginImages.get(JavaPluginImages.IMG_FIELD_DEFAULT);
        } else if (node instanceof PropertyNode) {
            // TODO: need compound icon with 'r' and 'w' on it to indicate property and access.
            int mods = ((PropertyNode) node).getModifiers();
            if (test(mods, Opcodes.ACC_PUBLIC)) {
                return JavaPluginImages.get(JavaPluginImages.IMG_FIELD_PUBLIC);
            } else if (test(mods, Opcodes.ACC_PROTECTED)) {
                return JavaPluginImages.get(JavaPluginImages.IMG_FIELD_PROTECTED);
            } else if (test(mods, Opcodes.ACC_PRIVATE)) {
                return JavaPluginImages.get(JavaPluginImages.IMG_FIELD_PRIVATE);
            }
            return JavaPluginImages.get(JavaPluginImages.IMG_FIELD_DEFAULT);
        }
        return null;
    }
    private boolean test(int flags, int mask) {
        return (flags & mask) != 0;
    }
    
    /**
     * Very simple way to calculate relevance based on name
     */
    protected int getRelevance(char[] name) {
        switch(name[0]) {
            case '$':
                return 1;
            case '_':
                return 20;
            default:
                return 100;
        }
    }

}
