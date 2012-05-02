
/* First created by JCasGen Wed May 02 22:44:52 CEST 2012 */
package com.github.fhirschmann.clozegen.lib.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Wed May 02 23:03:20 CEST 2012
 * @generated */
public class Distractor_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Distractor_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Distractor_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Distractor(addr, Distractor_Type.this);
  			   Distractor_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Distractor(addr, Distractor_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Distractor.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.github.fhirschmann.clozegen.lib.type.Distractor");
 
  /** @generated */
  final Feature casFeat_test;
  /** @generated */
  final int     casFeatCode_test;
  /** @generated */ 
  public String getTest(int addr) {
        if (featOkTst && casFeat_test == null)
      jcas.throwFeatMissing("test", "com.github.fhirschmann.clozegen.lib.type.Distractor");
    return ll_cas.ll_getStringValue(addr, casFeatCode_test);
  }
  /** @generated */    
  public void setTest(int addr, String v) {
        if (featOkTst && casFeat_test == null)
      jcas.throwFeatMissing("test", "com.github.fhirschmann.clozegen.lib.type.Distractor");
    ll_cas.ll_setStringValue(addr, casFeatCode_test, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Distractor_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_test = jcas.getRequiredFeatureDE(casType, "test", "uima.cas.String", featOkTst);
    casFeatCode_test  = (null == casFeat_test) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_test).getCode();

  }
}



    