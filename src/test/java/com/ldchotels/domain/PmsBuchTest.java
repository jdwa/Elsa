package com.ldchotels.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ldchotels.protel.domain.PmsBuch;
import com.ldchotels.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class PmsBuchTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PmsBuch.class);
        PmsBuch pmsBuch1 = new PmsBuch();
        pmsBuch1.setId(1L);
        PmsBuch pmsBuch2 = new PmsBuch();
        pmsBuch2.setId(pmsBuch1.getId());
        assertThat(pmsBuch1).isEqualTo(pmsBuch2);
        pmsBuch2.setId(2L);
        assertThat(pmsBuch1).isNotEqualTo(pmsBuch2);
        pmsBuch1.setId(null);
        assertThat(pmsBuch1).isNotEqualTo(pmsBuch2);
    }
}
