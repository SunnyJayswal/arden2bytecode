// arden2bytecode
// Copyright (c) 2010, Daniel Grunwald
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without modification, are
// permitted provided that the following conditions are met:
//
// - Redistributions of source code must retain the above copyright notice, this list
//   of conditions and the following disclaimer.
//
// - Redistributions in binary form must reproduce the above copyright notice, this list
//   of conditions and the following disclaimer in the documentation and/or other materials
//   provided with the distribution.
//
// - Neither the name of the owner nor the names of its contributors may be used to
//   endorse or promote products derived from this software without specific prior written
//   permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS &AS IS& AND ANY EXPRESS
// OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
// AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
// CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
// DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
// DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
// IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
// OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

package arden.compiler;

import java.lang.reflect.Modifier;

import arden.codegenerator.FieldReference;
import arden.compiler.node.TIdentifier;
import arden.runtime.ArdenRunnable;

/** INTERFACE Variables */
final class InterfaceVariable extends CallableVariable {

	protected InterfaceVariable(TIdentifier varName, FieldReference runnableField) {
		super(varName, runnableField);
	}

	/** Gets the InterfaceVariable for the LHSR, or creates it on demand. */
	public static InterfaceVariable getVariable(CodeGenerator codeGen, LeftHandSideResult lhs) {
		if (!(lhs instanceof LeftHandSideIdentifier))
			throw new RuntimeCompilerException(lhs.getPosition(), "INTERFACE variables must be simple identifiers");
		TIdentifier ident = ((LeftHandSideIdentifier) lhs).identifier;
		Variable variable = codeGen.getVariable(ident.getText());
		if (variable instanceof InterfaceVariable) {
			return (InterfaceVariable) variable;
		} else {
			FieldReference mlmField = codeGen.createField(ident.getText(), ArdenRunnable.class, Modifier.PRIVATE);
			InterfaceVariable cv = new InterfaceVariable(ident, mlmField);
			codeGen.addVariable(cv);
			return cv;
		}
	}
}
