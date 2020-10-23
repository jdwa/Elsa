package com.ldchotels.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ldchotels.protel.domain.PmsKunden;
import com.ldchotels.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class PmsKundenTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PmsKunden.class);
        PmsKunden pmsKunden1 = new PmsKunden();
        pmsKunden1.setId(1L);
        PmsKunden pmsKunden2 = new PmsKunden();
        pmsKunden2.setId(pmsKunden1.getId());
        assertThat(pmsKunden1).isEqualTo(pmsKunden2);
        pmsKunden2.setId(2L);
        assertThat(pmsKunden1).isNotEqualTo(pmsKunden2);
        pmsKunden1.setId(null);
        assertThat(pmsKunden1).isNotEqualTo(pmsKunden2);
    }
}
